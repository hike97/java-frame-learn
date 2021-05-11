package designpattern.factorymethod;

/**
 * @author hike97 2month
 * @create 2021-05-08 19:40
 * @desc
 **/
public class Main {
    public static void main (String[] args) {
        Moveable m = new Broom ();
        m.go ();
    }
}
