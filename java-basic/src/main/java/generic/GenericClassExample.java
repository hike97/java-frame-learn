package generic;

import lombok.Data;

/**
 * @author hike97
 * @create 2021-06-05 13:02
 * @desc 泛型例子实体
 **/
@Data
public class GenericClassExample<T> {
    //member 这个成员变量的类型为T,T的类型由外部指定
    private T member;

    public GenericClassExample (T member) {
        this.member = member;
    }

    public T handleSomething (T target) {
        return target;
    }

    //泛型类中可以使用普通方法
    public String sayHello (String name) {
        return "Hello" + name;
    }

    /**
     * 只有声明泛型标识符的才是泛型方法<E> 相反则不是泛型方法
     *
     * @param inputArray
     * @param <E>        泛型标识符 独立与泛型类
     */
    public static <T> void printArray (T[] inputArray) {
        for (int i = 0; i < inputArray.length; i++) {
            System.out.printf ("%s", inputArray[i]);
            System.out.printf (" ");
        }
        System.out.println ();
    }

}
