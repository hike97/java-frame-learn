package reflect.aop;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@_Aspect (type = reflect.aop.PrintUsageAspect.class)
public class Order implements IOrder {

    int state = 0;

    @Override
    public void pay () throws InterruptedException {
        Thread.sleep (50);
        this.state = 1;
    }

    @Override
    public void show () {
        System.out.println ("order status:" + this.state);
    }


    @Test
    public void test_proxy () throws InterruptedException {

        var order = new Order ();
        var proxy = (IOrder) Proxy.newProxyInstance (
                Order.class.getClassLoader (),
                new Class[]{IOrder.class},
                new InvocationHandler () {
                    @Override
                    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println ("before invoke method:" + method);
                        return method.invoke (order);
                    }
                }
        );
        proxy.pay ();

    }

    /**
     * Aspect test
     */
    @Test
    public void AspectTest () throws Exception {
        /**
         *  MARK :这里一定要注意 是 IOrder 因为代理的是接口
         */
        IOrder order = Aspect.getProxy (Order.class, "reflect.aop.PrintUsageAspect", "reflect.aop.TimeUsageAspect");
        order.pay ();
        order.show ();
    }

    /**
     * @param args
     * @throws Exception
     * @_Aspect 实现aop
     */
    public static void main (String[] args) throws Exception {
        IOrder order = Aspect.newInstance (Order.class);
        order.pay ();
        order.show ();
    }
}
