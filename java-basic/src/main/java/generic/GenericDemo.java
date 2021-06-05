package generic;

/**
 * @author hike97
 * @create 2021-06-05 13:02
 * @desc 泛型测试类
 * 常见泛型类的代表
 * E-enumeration集合中使用
 * T-type java类
 * K-key键
 * V-value值
 * N-Number数值类型
 **/
public class GenericDemo {
    public static void handleMember (GenericClassExample<Integer> intValue) {
        int result = 111 + intValue.getMember ();
        System.out.println ("result is " + result);
    }

    /**
     * 使用通配符让泛型支持继承
     *
     * @param intValue
     */
    public static void handleMember01 (GenericClassExample<?> intValue) {
        int result = 111 + (Integer) intValue.getMember ();
        System.out.println ("result is " + result);
    }

    /**
     * 给泛型加入上边界 ? extends E ? extends Number 父类为Number
     *
     * @param intValue
     */
    public static void handleMemberExtends (GenericClassExample<? extends Number> intValue) {
        int result = 111 + (Integer) intValue.getMember ();
        System.out.println ("result is " + result);
    }

    /**
     * 给泛型加入下边界 ? super E < ? super Integer子类为Integer
     *
     * @param intValue
     */
    public static void handleMemberSuper (GenericClassExample<? super Integer> intValue) {
        int result = 111 + (Integer) intValue.getMember ();
        System.out.println ("result is " + result);
    }


    public static void main (String[] args) {
        GenericClassExample<String> stringExample = new GenericClassExample<> ("abc");
        GenericClassExample<Integer> integerExample = new GenericClassExample<> (123);
        System.out.println (stringExample.getMember ().getClass ());
        System.out.println (integerExample.getMember ().getClass ());
        integerExample.sayHello ("haha");
        //泛型不支持基本数据类型
        //        GenericClassExample<int>
        //泛型相关的信息不会进入运行时阶段
        System.out.println (stringExample.getClass ());
        System.out.println (integerExample.getClass ());
        //javac  -cp E:\m2_repo\repository\org\projectlombok\lombok\1.18.12\lombok-1.18.12.jar -encoding utf-8 *.java
        //查看类型:类型擦除
        /*
         GenericClassExample var1 = new GenericClassExample("abc");
         GenericClassExample var2 = new GenericClassExample(123);
        */
        handleMember (integerExample);
        /**
         * 调用泛型方法
         */
        Integer[] integers = {1, 2, 3, 4, 5, 6};
        Double[] doubles = {1.3, 1.2, 1.3, 1.4, 1.5};
        Character[] characters = {'A', 'B', 'V'};
        GenericClassExample.printArray (integers);
        GenericClassExample.printArray (doubles);
        GenericClassExample.printArray (characters);
    }
}
