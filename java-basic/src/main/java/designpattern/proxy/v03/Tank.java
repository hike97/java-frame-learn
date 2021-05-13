package designpattern.proxy.v03;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * CGlib 动态代理 不需要实现接口 动态代理底层实现方式 是asm 请看spring-reviewer 下的proxy asm
 * CGlib 优点  不需要接口
 * CGlib 缺点  无法代理finalClass
 */
public class Tank  {

    public void move() {
        System.out.println("Tank moving claclacla...");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer ();
        enhancer.setSuperclass (Tank.class);
        Tank tank1 = new Tank ();
        enhancer.setCallback (new MethodInterceptor () {
            @Override
            public Object intercept (Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
                System.out.println ("Object o 生成对象是坦克的子类："+o.getClass ().getSuperclass ().getName ());
                System.out.println ("start method");
                //这里也可以用method
//                Object result = proxy.invokeSuper (o, objects);
                Object result = method.invoke (tank1, args);
                System.out.println ("end method");
                return result;
            }
        });
        Tank tank = (Tank) enhancer.create ();
        tank.move ();
    }
}

