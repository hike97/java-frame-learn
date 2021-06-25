package collection;

import java.util.Arrays;
import java.util.List;

/**
 * @author hike97
 * @create 2021-06-14 23:26
 * @desc Collection接口常用方法
 **/
public class CollectionInterfaceDemo {
    public static void main (String[] args) {
        List<String> list = Arrays.asList ("Java", "Python", "Scala", "Go");
        //Array转数组
        String[] arr = list.toArray (new String[0]);
        System.out.println (Arrays.toString (arr));
        //用函数式编程 :: Method Reference  Lazy evaluation
        String[] strings = list.toArray (String[]::new);
        System.out.println (Arrays.toString (strings));
        //Collection 容器
        //Set 集合 不重复的容器
    }
}
