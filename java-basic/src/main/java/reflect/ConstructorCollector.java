package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author:
 * @create: 2020-03-14 14:05
 */
public class ConstructorCollector {
    /**
     * 反射：运行时查看，反观程序内部
     */

    public static void main (String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName ("cn.airfei.demo.reflect.RefectTarget");
        System.out.println ("*********获取所有公有构造方法××××××××××××××××××××");
        Constructor[] constructorArr = clazz.getConstructors ();
        for (Constructor constructor : constructorArr) {
            System.out.println (constructor);
        }

        System.out.println ("*********获取所有构造方法××××××××××××××××××××");
        constructorArr = clazz.getDeclaredConstructors ();
        for (Constructor constructor : constructorArr) {
            System.out.println (constructor);
        }


        System.out.println ("*********获取所有单个公有构造方法××××××××××××××××××××");
        Constructor cons = clazz.getConstructor (String.class, int.class);
        System.out.println ("cons = " + cons);

        System.out.println ("*********获取所有单个构造方法(包含私有)××××××××××××××××××××");
        cons = clazz.getDeclaredConstructor (int.class);
        System.out.println ("cons = " + cons);
        // 忽略调访问修饰符
        cons.setAccessible (true);
        RefectTarget reflectTarget = (RefectTarget) cons.newInstance (12);


    }
}
