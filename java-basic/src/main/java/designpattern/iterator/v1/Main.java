package designpattern.iterator.v1;

import designpattern.iterator.Collection_;
import designpattern.iterator.Iterator_;

import java.util.Arrays;

/**
 * @author hike97 2month
 * @create 2021-05-12 20:53
 * @desc 迭代器模式 使用数组模拟ArrayList
 **/
public class Main {
    public static void main (String[] args) {
        ArrayList_ list_ = new ArrayList_ ();
        for (int i = 0; i < 15; i++) {
            list_.add (i);
        }
        System.out.println (list_.size ());
        Iterator_ iterator = list_.iterator ();
        while (iterator.hasNext ()){
            System.out.println (iterator.next ());
        }
    }

}

class ArrayList_ implements Collection_ {
    Object[] objects = new Object[10];
    private int index = 0;
    @Override
    public void add(Object o) {
        /**
         * 数组动态拓展
         */
        if (index == objects.length) {
            Object[] newObjects = new Object[this.objects.length * 2];
            System.arraycopy (objects,0,newObjects,0,objects.length);
            objects = newObjects;
        }
        objects[index] = o;
        index++;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public Iterator_ iterator () {
        final int[] currentIndex = {0};
        return new Iterator_ () {
            @Override
            public boolean hasNext () {
                if (currentIndex[0] >=index){
                    return false;
                }
                return true;
            }

            @Override
            public Object next () {
                Object o = objects[currentIndex[0]];
                currentIndex[0]++;
                return o;
            }
        };
    }

    @Override
    public String toString () {
        return "ArrayList_{" +
                "objects=" + Arrays.toString (objects) +
                ", index=" + index +
                '}';
    }
}
