package designpattern.singleton.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/7
 * @return
 */
public class SingletonDemo {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        System.out.println(EnumStarvingSingleton.getInstance());
        Class<EnumStarvingSingleton> enumSingletonClass = EnumStarvingSingleton.class;
        Constructor<EnumStarvingSingleton> constructor = enumSingletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        EnumStarvingSingleton enumStarvingSingleton = constructor.newInstance();
        System.out.println(enumStarvingSingleton);//与上面的打印结果不同，说明反射已经攻破外围防线
        System.out.println(enumStarvingSingleton.getInstance());//打印结果相同，暂时没攻破
        System.out.println("============================================");
        System.out.println(StarvingSingleton.getInstance());
        Class singletonClass = StarvingSingleton.class;
        Constructor declaredConstructor = singletonClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        System.out.println(declaredConstructor.newInstance());//打印结果不同，已经攻破
    }
}