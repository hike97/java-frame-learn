package jvm.start;

import java.io.Serializable;

/**
 * @author hike97
 * @create 2021-08-11 13:48
 * @desc ��class
 **/
public class BlankClass implements Cloneable, Serializable {
    int i = 0;
    String s = "Hello World";

    public BlankClass (int i, String s) {
        this.i = i;
        this.s = s;
    }

    public void m () {
    }
}
