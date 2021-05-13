package designpattern.proxy.v02;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * 使用jdk 动态代理  相关源码类ProxyGenerator MethodNode
 */
public class Tank implements Movable {

    /**
     * 模拟坦克移动了一段儿时间
     */
    @Override
    public void move() {
        System.out.println("Tank moving claclacla...");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Tank tank = new Tank();
        //匿名内部类写法
//        Movable o = (Movable) Proxy.newProxyInstance(Tank.class.getClassLoader(), new Class[]{Movable.class}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("tank start move");
//                Object o = method.invoke(tank, args);
//                System.out.println("tank end move");
//                return o;
//            }
//        });
        //外部类实现接口
        Movable o = (Movable) Proxy.newProxyInstance(
                Tank.class.getClassLoader(),
                Tank.class.getInterfaces(),
                new Loghandler(tank));
//        o.move();
        o.toString();
    }
}

interface Movable {
    void move();
}

//其中的匿名内部类也可以提出来
class Loghandler implements InvocationHandler{
    private Object target;

    public Loghandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = null;
        if ("toString".equals(method.getName())){
            System.out.println("toString方法增强");
            o = method.invoke(target, args);
            System.out.println(o);
        }else {
            System.out.println("tank start move 方法名:"+method.getName());
            //o 为方法返回值 method 返回值为void 所以 o是空
            o = method.invoke(target, args);
            System.out.println("tank end move ");
            System.out.println(null == o);
        }
        return o;
    }
}

