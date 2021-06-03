package com.atguigu.springboot.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName GoodController
 * @Description TODO
 * @Author Oneby
 * @Date 2021/2/2 18:59
 * @Version 1.0
 */
@RestController
public class GoodController {

    /**
     * V2版本:代码中使用分布式锁
     * <p>
     * Redis具有极高的性能，且其命令对分布式锁支持友好，借助 SET 命令即可实现加锁处理
     * The SET command supports a set of options that modify its behavior:
     * EX seconds – Set the specified expire time, in seconds.
     * PX milliseconds – Set the specified expire time, in milliseconds.
     * EXAT timestamp-seconds – Set the specified Unix time at which the key will expire, in seconds.
     * PXAT timestamp-milliseconds – Set the specified Unix time at which the key will expire, in milliseconds.
     * NX – Only set the key if it does not already exist.
     * XX – Only set the key if it already exist.
     * KEEPTTL – Retain the time to live associated with the key.
     * GET – Return the old value stored at key, or nil when key did not exist.
     *
     * @return
     */

    private static final String REDIS_LOCK_KEY = "lock";
    /**
     * 最终版 使用redis分布式锁 redisson（源码需要了解）
     *
     * @return
     */
    //注入redisson
    @Autowired
    Redisson redisson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value ("${server.port}")
    private String serverPort;

    @GetMapping ("/buy_goods")
    public String buy_Goods () {
        // 从 redis 中获取商品的剩余数量
        String result = stringRedisTemplate.opsForValue ().get ("goods:001");
        int goodsNumber = result == null ? 0 : Integer.parseInt (result);
        String retStr = null;

        // 商品数量大于零才能出售
        if (goodsNumber > 0) {
            int realNumber = goodsNumber - 1;
            stringRedisTemplate.opsForValue ().set ("goods:001", realNumber + "");
            retStr = "你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
        } else {
            retStr = "商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;
        }
        System.out.println (retStr);
        return retStr;
    }

    /**
     * v1 版本 机上jvm单机锁
     * //synchronized 还是 trylock 在实际应用中需要取舍
     * //        Lock lock = ...
     * //        if (lock.tryLock ()) {
     * //            lock.tryLock (1, TimeUnit.SECONDS);
     * //            try {
     * //
     * //            }finally {
     * //                lock.unlock ();
     * //            }
     * //        }else {
     * //
     * //        }
     *
     * @return
     */
    @GetMapping ("/buy_goods/v1")
    public String buyGoodsVersion01 () {
        synchronized (this) {
            // 从 redis 中获取商品的剩余数量
            String result = stringRedisTemplate.opsForValue ().get ("goods:001");
            int goodsNumber = result == null ? 0 : Integer.parseInt (result);
            String retStr = null;

            // 商品数量大于零才能出售
            if (goodsNumber > 0) {
                int realNumber = goodsNumber - 1;
                stringRedisTemplate.opsForValue ().set ("goods:001", realNumber + "");
                retStr = "[" + Thread.currentThread ().getName () + " ] 你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
            } else {
                retStr = "[" + Thread.currentThread ().getName () + " ] 商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;
            }
            System.out.println (retStr);
            return retStr;
        }
    }

    @GetMapping ("/buy_goods/v2")
    public String buyGoodsVersion02 () {
        //当前uuid+线程名
        String value = UUID.randomUUID ().toString () + Thread.currentThread ().getName ();
        /*
         * v2.1:当前版本存在弊端：如果获得线程获得锁执行到一半机器宕机，就会导致锁无法释放，其它线程一直阻塞
         * */
        //            Boolean flag = stringRedisTemplate.opsForValue ().setIfAbsent (REDIS_LOCK_KEY, value);
        /*
         *v2.2:给锁加上过期时间 10s : 但还是有问题 这两句操作不是原子的 所以还需要改进
         */
        //            stringRedisTemplate.expire (REDIS_LOCK_KEY,10L,TimeUnit.SECONDS);
        /*
         *v2.3:进行原子操作 :出现问题
         *  1.锁的时间 > 业务时间 会导致阻塞性能损耗
         *  2.锁的时间 < 业务时间 比较严重会导致误删其他人的锁的现象
         *   T1--|-获取锁----10s锁超时释放锁---T1业务正在执行------T1执行完把T2锁删除了------T1退出
         *       |              |                                                      |
         *     T2开始         T2获取锁
         *     业务等待        执行任务-------------------------------------------------T2执行完毕---释放锁发现锁没了？？？
         * 解决方案：只允许删除自己的锁，不允许删除别人的锁  详见v2.4
         * */
        Boolean flag = stringRedisTemplate.opsForValue ().setIfAbsent (REDIS_LOCK_KEY, value, 10L, TimeUnit.SECONDS);

        if (!flag) {
            return "抢锁失败";
        }
        try {
            // 从 redis 中获取商品的剩余数量
            String result = stringRedisTemplate.opsForValue ().get ("goods:001");
            int goodsNumber = result == null ? 0 : Integer.parseInt (result);
            String retStr = null;

            // 商品数量大于零才能出售
            if (goodsNumber > 0) {
                int realNumber = goodsNumber - 1;
                stringRedisTemplate.opsForValue ().set ("goods:001", realNumber + "");
                retStr = "[" + Thread.currentThread ().getName () + " ] 你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
            } else {
                retStr = "[" + Thread.currentThread ().getName () + " ] 商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;
            }
            System.out.println (retStr);
            return retStr;
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        } finally {

            /*
               v2.4判断是自己的锁才去删除当前锁
               弊端：判断和删除操作非原子性 如果这把锁不是当前客户端 则会误解锁
               解决方案：1.lua脚本
                        2.redis 事务
             */
            if (value.equalsIgnoreCase (stringRedisTemplate.opsForValue ().get (REDIS_LOCK_KEY))) {
                //释放分布式锁
                stringRedisTemplate.delete (REDIS_LOCK_KEY);
            }
            /*
               v2.5 加事务 watch multi op exec unwatch
             */
            while (true) {
                stringRedisTemplate.watch (REDIS_LOCK_KEY);
                if (value.equalsIgnoreCase (stringRedisTemplate.opsForValue ().get (REDIS_LOCK_KEY))) {
                    //开启事务
                    stringRedisTemplate.setEnableTransactionSupport (true);
                    stringRedisTemplate.multi ();
                    //释放分布式锁
                    stringRedisTemplate.delete (REDIS_LOCK_KEY);
                    List<Object> list = stringRedisTemplate.exec ();
                    if (list == null) {
                        continue;
                    }
                }
                //如果删除成功 释放监控器 break 跳出档期那循环
                stringRedisTemplate.unwatch ();
                break;
            }
            /*
              2.6 执行lua脚本
             */
            DefaultRedisScript script = new DefaultRedisScript ();
            String lua = "if redis.call('get', KEYS[1]) == ARGV[1]" + "then "
                    + "return redis.call('del', KEYS[1])" + "else " + "  return 0 " + "end";
            script.setScriptSource (new StaticScriptSource (lua));
            script.setResultType (Integer.class);
            Object result = stringRedisTemplate.execute (script, Arrays.asList (REDIS_LOCK_KEY), Arrays.asList (value));
            // 获取 lua 脚本的执行结果
            if ("1".equals (result.toString ())) {
                System.out.println ("------del REDIS_LOCK_KEY success");
            } else {
                System.out.println ("------del REDIS_LOCK_KEY error");
            }
        }
        return null;
    }

    /**
     * lua 脚本 ：无法解决 换存续期 集群上无法实现
     * 1.redis 异步复制造成的锁丢失， 比如：主节点没来的及把刚刚 set 进来这条数据给从节点，就挂了，那么主节点和从节点的数据就不一致。此时如果集群模式下，就得上 Redisson 来解决
     * 2.对比zk：zookeeper 保持强一致性原则，对于集群中所有节点来说，要么同时更新成功，要么失败，因此使用 zookeeper 集群并不存在主从节点数据丢失的问题，但丢失了速度方面的性能
     */

    @GetMapping ("/buy_goods/lua")
    public String buyGoodsVersionLua () {
        //当前uuid+线程名
        String value = UUID.randomUUID () + Thread.currentThread ().getName ();
        Boolean flag = stringRedisTemplate.opsForValue ().setIfAbsent (REDIS_LOCK_KEY, value, 10L, TimeUnit.SECONDS);

        if (!flag) {
            return "抢锁失败";
        }
        try {
            // 从 redis 中获取商品的剩余数量
            String result = stringRedisTemplate.opsForValue ().get ("goods:001");
            int goodsNumber = result == null ? 0 : Integer.parseInt (result);
            String retStr = null;

            // 商品数量大于零才能出售
            if (goodsNumber > 0) {
                int realNumber = goodsNumber - 1;
                stringRedisTemplate.opsForValue ().set ("goods:001", realNumber + "");
                retStr = "[" + Thread.currentThread ().getName () + " ] 你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
            } else {
                retStr = "[" + Thread.currentThread ().getName () + " ] 商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;
            }
            System.out.println (retStr);
            return retStr;
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        } finally {
            /*
              2.6 执行lua脚本
             */
            DefaultRedisScript script = new DefaultRedisScript ();
            String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<> (RELEASE_LOCK_LUA_SCRIPT, Long.class);
            Long result = stringRedisTemplate.execute (redisScript, Collections.singletonList (REDIS_LOCK_KEY), value);
            // 获取 lua 脚本的执行结果
            if ("1".equals (result.toString ())) {
                System.out.println ("------del REDIS_LOCK_KEY success");
            } else {
                System.out.println ("------del REDIS_LOCK_KEY error");
            }
        }
        return null;
    }

    @GetMapping ("/buy_goods/redisson")
    public String buy_GoodsFinal () {
        String value = UUID.randomUUID () + Thread.currentThread ().getName ();
        RLock lock = redisson.getLock (REDIS_LOCK_KEY);
        lock.lock ();
        try {
            String result = stringRedisTemplate.opsForValue ().get ("goods:001");
            int goodsNumber = result == null ? 0 : Integer.parseInt (result);
            String retStr;
            if (goodsNumber > 0) {
                int realNumber = goodsNumber - 1;
                stringRedisTemplate.opsForValue ().set ("goods:001", realNumber + "");
                retStr = "你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
            } else {
                retStr = "商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;
            }
            System.out.println (retStr);
            return retStr;
        } finally {
            if (lock.isLocked () && lock.isHeldByCurrentThread ()) {
                lock.unlock ();
            }
        }
    }

}
