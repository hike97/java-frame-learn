package juc.aqs;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class T01_HelloVarHandle {
    /**
     * VarHandle 特点 jdk9 才有
     * 1.普通属性原子操作
     * 2.比反射快，直接操纵二进制码
     */

    int x = 8;

    private static VarHandle handle;

    static {
        try {
            handle = MethodHandles.lookup ().findVarHandle (T01_HelloVarHandle.class, "x", int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace ();
        } catch (IllegalAccessException e) {
            e.printStackTrace ();
        }
    }

    public static void main (String[] args) {
        T01_HelloVarHandle t = new T01_HelloVarHandle ();

        //plain read / write
        System.out.println ((int) handle.get (t));
        handle.set (t, 9);
        System.out.println (t.x);

        handle.compareAndSet (t, 9, 10);
        System.out.println (t.x);

        handle.getAndAdd (t, 10);
        System.out.println (t.x);

    }
}
