package designpattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author hike97
 * @create 2021-06-06 0:50
 * @desc 饿汉模式枚举类加载 这种模式无视反射和序列化入侵
 **/
public class EnumStarvingSingleton {

    private EnumStarvingSingleton () {
    }

    public static EnumStarvingSingleton getInstance () {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        /**
         * 枚举类holder
         */
        HOLDER;
        private EnumStarvingSingleton instance;

        ContainerHolder () {
            this.instance = new EnumStarvingSingleton ();
        }
    }

    public static void main (String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //测试反射是否能入侵此方法
        EnumStarvingSingleton instance = EnumStarvingSingleton.getInstance ();
        System.out.println ("代码创建的实例：" + instance);
        //反射创建的实例
        Class<EnumStarvingSingleton> clazz = EnumStarvingSingleton.class;
        Constructor<EnumStarvingSingleton> constructor = clazz.getDeclaredConstructor ();
        EnumStarvingSingleton enumStarvingSingleton = constructor.newInstance ();
        EnumStarvingSingleton reflectInstance = enumStarvingSingleton.getInstance ();
        System.out.println (enumStarvingSingleton);
        System.out.println ("反射获得的实例" + reflectInstance);
        //尝试获取枚举类中的方法
        System.out.println ("获取枚举类的实例" + reflectInstance);
        Class<ContainerHolder> aClass = ContainerHolder.class;
        Constructor<ContainerHolder> constructor1 = aClass.getDeclaredConstructor ();
        constructor1 = aClass.getDeclaredConstructor (String.class, Integer.class);
        constructor1.newInstance (true);
        System.out.println (constructor1.newInstance ());
    }

   /*
    为什么反射无法入侵？
    反编译得到代码块 说明 Instance在类加载的时候就已经执行，相关holder实例已经被创建出来 证明该单例模式时饿汉模式
    static
    {
        HOLDER = new EnumStarvingSingleton$ContainerHolder("HOLDER", 0);
        $VALUES = (new EnumStarvingSingleton$ContainerHolder[] {
                HOLDER
        });
    }*/
    /*
      为什么序列化无法入侵？
      ObjectInputStream 的 readObject 方法
     */
}
