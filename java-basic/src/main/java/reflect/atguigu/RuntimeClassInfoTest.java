package reflect.atguigu;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @author hike97 2month
 * @create 2020-11-21 18:51
 * @desc 获取运行时类信息
 **/
public class RuntimeClassInfoTest {

    public static Class<?> clazz;

    static {
        try {
            clazz = Class.forName ("reflect.atguigu.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }

    /**
     * 属性测试
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void test1 () {


        //getfields 获取当前类 和父类的 public属性
        System.out.println ("##############################getFields () 获取当前类 和父类的 public属性");
        for (Field field : clazz.getFields ()) {
            System.out.println (field);
        }
        System.out.println ();
        System.out.println ("##############################getDeclaredFields () 获取当前运行类中声明的所有属性（不包含父类中声明的属性）");
        for (Field field : clazz.getDeclaredFields ()) {
            System.out.println ("属性：" + field);
            System.out.println ("权限修饰符：" + field.getModifiers () + " =>转换后的权限：" + Modifier.toString (field.getModifiers ()));
            System.out.println ("数据类型：" + field.getType ().getName ());
            System.out.println ("变量名： " + field.getName ());
            System.out.println ();
        }
    }

    /**
     * 方法测试
     */
    @Test
    public void test2 () {
        System.out.println ("##########################################getMethods ()，获取运行时类的及其父类的public方法");
        for (Method method : clazz.getMethods ()) {
            System.out.println (method);
        }
        System.out.println ();
        System.out.println ("##########################################getMethods ()，获取运行时类的中的所有方法（不包括父类）");
        for (Method method : clazz.getDeclaredMethods ()) {
            System.out.println ("当前方法：" + method);
            System.out.println ("当前方法注解：" + Arrays.toString (method.getAnnotations ()));
            System.out.println ("当前方法权限修饰符：" + Modifier.toString (method.getModifiers ()));
            System.out.println ("当前方法返回值：" + method.getReturnType ());
            System.out.println ("当前方法名：" + method.getName ());
            System.out.println ("当前方法参数类型：" + Arrays.toString (method.getParameterTypes ()));
            System.out.println ("当前方法抛出异常：" + Arrays.toString (method.getExceptionTypes ()));
            System.out.println ();
        }

    }

    /**
     * 其它测试
     */
    @Test
    public void test3 () {
        System.out.println ("##############################getConstructors () 获取当前类 和父类的 public构造方法");
        for (Constructor<?> constructor : clazz.getConstructors ()) {
            System.out.println (constructor);
        }
        System.out.println ();
        System.out.println ("##############################getDeclaredFields () 获取当前运行类中声明的所有构造方法（不包含父类中声明的属性）");
        for (Constructor<?> constructor : clazz.getDeclaredConstructors ()) {
            System.out.println (constructor);
        }
        System.out.println ();
        System.out.println ("##############################getSuperclass () 获取运行时类的父类");
        System.out.println (clazz.getSuperclass ());
        System.out.println ("##############################getSuperclass () 获取运行时类带泛型的父类");
        System.out.println (clazz.getGenericSuperclass ());
        System.out.println ("父类的泛型：" + Arrays.toString ((((ParameterizedType) clazz.getGenericSuperclass ()).getActualTypeArguments ())));
        System.out.println ("#####################getInterfaces ()获取运行时类的接口：" + Arrays.toString (clazz.getInterfaces ()));
        System.out.println ("获取运行时类的接口：" + Arrays.toString (clazz.getSuperclass ().getInterfaces ()));
        System.out.println ("获取运行时类的所在包：" + clazz.getPackage ());


    }

    /**
     * 方法调用
     */
    @Test
    public void test4 () throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //		Object newInstance = (Person)clazz.newInstance ();
        Constructor<?> constructor = clazz.getDeclaredConstructor (String.class);
        constructor.setAccessible (true);
        Object newInstance = constructor.newInstance ("TOM");
        System.out.println (newInstance);
        Method show = clazz.getDeclaredMethod ("show", String.class);
        show.setAccessible (true);
        show.invoke (newInstance, "清华腚姐");
    }
}
