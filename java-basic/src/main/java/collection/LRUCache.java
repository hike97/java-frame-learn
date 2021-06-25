package collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author hike97
 * @create 2021-06-15 7:14
 * @desc linkedHashMap实现LRU
 **/
public class LRUCache<K, V> implements Iterable<K> {
    int MAX = 3;
    LinkedHashMap cache = new LinkedHashMap<> ();

    public void cache (K key, V value) {
        //如果存在key 就将当前key删掉,重新放到末尾刷新
        if (cache.containsKey (key)) {
            cache.remove (key);
        } else if (cache.size () >= MAX) {
            //如果到达阈值 那么就将第一个删除
            Iterator it = cache.keySet ().iterator ();
            Object first = it.next ();
            cache.remove (first);
        }
        cache.put (key, value);
    }

    @Override
    public Iterator<K> iterator () {
        return cache.keySet ().iterator ();
    }

    public static void main (String[] args) {
        var lru = new LRUCache<String, Integer> ();
        lru.cache ("A", 1);
        lru.cache ("B", 2);
        lru.cache ("C", 3);

        lru.cache ("D", 4);
        lru.cache ("C", 10);
        System.out.println (
                "leave <-" +
                        StreamSupport.stream (lru.spliterator (), false)
                                .map (Object::toString)
                                .collect (Collectors.joining ("<-"))
        );
        System.out.println (Runtime.getRuntime ().availableProcessors ());
    }
}
