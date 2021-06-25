package juc.aqs.cas.unsafe;


import sun.misc.Unsafe;

/**
 * @author hike97 2month
 * @create 2021-05-16 13:49
 * @desc unsafe descript
 **/
public class HelloUnsafe {
    //unsafe 类的使用
    static class M {
        private M () {
        }

        int i = 0;
    }

    public static void main (String[] args) throws InstantiationException {
        Unsafe unsafe = Unsafe.getUnsafe ();
        M m = (M) unsafe.allocateInstance (M.class);
        m.i = 9;
        System.out.println (m.i);
    }
}
