package collection;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class HashSetvsTreeSet {

    @Test
    public void test_order () {
        var hashSet = new HashSet<Integer> ();
        hashSet.add (3);
        hashSet.add (7);
        hashSet.add (2);
        hashSet.add (81);


        System.out.println (hashSet.stream ().map (x -> x.toString ()).collect (Collectors.joining (",")));
        /*
           treeSet  NavigableSet SortedSet Set Collection
         */
        var y = 4;
        var treeSet = new TreeSet<Integer> () {
            {
                add (3);
                add (7);
                add (2);
                add (81);
                add (y);
            }
        };
        System.out.println ("y的前继节点：" + treeSet.lower (y));
        System.out.println ("y的后继节点：" + treeSet.higher (y));

        System.out.println ("less than or equal y的节点： " + treeSet.floor (5));
        System.out.println ("greater than or equal y的节点： " + treeSet.ceiling (5));


        System.out.println (treeSet.stream ().map (x -> x.toString ()).collect (Collectors.joining (",")));
    }


    @Test
    public void test_benchmark () {

        var random = new Random ();
        LinkedList<String> words = new LinkedList<> ();
        for (int i = 0; i < 1000000; i++) {

            var word = random.ints (97, 123)
                    .limit (12)
                    .collect (StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString ();

            words.add (word);
        }


        var hashSet = new HashSet<String> ();
        var treeSet = new TreeSet<String> ();

        var start = System.currentTimeMillis ();
        for (var w : words) {
            hashSet.add (w);
        }
        for (var w : words) {
            hashSet.contains (w);
        }
        System.out.println ("hashSet time:" + (System.currentTimeMillis () - start));

        start = System.currentTimeMillis ();
        for (var w : words) {
            treeSet.add (w);
        }
        for (var w : words) {
            treeSet.contains (w);
        }
        System.out.println ("treeSet time:" + (System.currentTimeMillis () - start));
    }

    public static void main (String[] args) {
        var random = new Random ();
        var word = random.ints (97, 123)
                .limit (12)
                .collect (StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString ();
        System.out.println (word);
    }

}
