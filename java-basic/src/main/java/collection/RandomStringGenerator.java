package collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author hike97
 * @create 2021-06-14 23:08
 * @desc 用Iterator接口生成随机字符串
 **/
public class RandomStringGenerator<T> implements Iterable<T> {

    private final List<T> list;

    public RandomStringGenerator (List<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator () {
        return new Iterator<T> () {
            @Override
            public boolean hasNext () {
                return true;
            }

            @Override
            public T next () {
                double random = Math.random ();
                System.out.println ("随机数：" + random);
                return list.get ((int) (list.size () * random));
            }
        };
    }

    public static void main (String[] args) {
        var list = Arrays.asList ("Java", "Python", "Scala", "Go");
        var listGen = new RandomStringGenerator<String> (list);
        for (int i = 0; i < 100; i++) {
            System.out.println (listGen.iterator ().next ());
        }
    }
}
