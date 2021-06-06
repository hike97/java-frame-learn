package com.simple.springboot.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    /**
     * 初始版本：
     *
     * @return
     */
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
        //synchronized 还是 trylock 在实际应用中需要取舍
        //        Lock lock = ...
        //        if (lock.tryLock ()) {
        //            lock.tryLock (1, TimeUnit.SECONDS);
        //            try {
        //
        //            }finally {
        //                lock.unlock ();
        //            }
        //        }else {
        //
        //        }
    }

    /**
     * lua 脚本
     */

    @GetMapping ("/buy_goods/lua")
    public String buyGoodsVersionLua () {
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
              2.6 执行lua脚本
             */
            DefaultRedisScript script = new DefaultRedisScript ();
            String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            script.setScriptSource (new StaticScriptSource (RELEASE_LOCK_LUA_SCRIPT));
            script.setResultType (Long.class);
            Object result = stringRedisTemplate.execute (script, Collections.singletonList (REDIS_LOCK_KEY), value);
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
