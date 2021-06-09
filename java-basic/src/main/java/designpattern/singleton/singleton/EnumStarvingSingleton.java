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
public class EnumStarvingSingleton {
    private EnumStarvingSingleton() {
    }

    private enum InnerEnum {
        INSTANCE;
        private EnumStarvingSingleton instance;

        InnerEnum() {
            instance = new EnumStarvingSingleton();
        }
    }

    public static EnumStarvingSingleton getInstance() {
        return InnerEnum.INSTANCE.instance;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Constructor constructor = InnerEnum.class.getDeclaredConstructor();
        Constructor constructor = InnerEnum.class.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        System.out.println(EnumStarvingSingleton.getInstance());
        System.out.println(constructor.newInstance());
    }
}
