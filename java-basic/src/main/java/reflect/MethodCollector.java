package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 14:42
 */
public class MethodCollector {

    public static void main (String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName ("cn.airfei.demo.reflect.RefectTarget");

        System.out.println ("*********获取所有公有方法(包括父类的,object)××××××××××××××××××××");
        Method[] methods = clazz.getMethods ();
        for (Method method : methods) {
            System.out.println (method);
        }

        System.out.println ("*********获取所有方法××××××××××××××××××××");
        methods = clazz.getDeclaredMethods ();
        for (Method method : methods) {
            System.out.println (method);
        }

        System.out.println ("*********获取单个公有方法(包括父类的,object)××××××××××××××××××××");
        Method method = clazz.getMethod ("show1", String.class);
        System.out.println (method);
        // 执行方法
        RefectTarget refectTarget = (RefectTarget) clazz.getConstructor ().newInstance ();
        method.invoke (refectTarget, "show1");


        System.out.println ("*********获取单个方法(包括私有)××××××××××××××××××××");
        method = clazz.getDeclaredMethod ("show4", int.class);
        method.setAccessible (true);
        Object show4 = method.invoke (refectTarget, 3453);
        System.out.println (show4);


    }

}
