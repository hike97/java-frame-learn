package designpattern.factorymethod;

/**
 * @author hike97 2month
 * @create 2021-05-08 19:37
 * @desc
 **/
public class Plane implements Moveable{
    @Override
    public void go(){
        System.out.println ("plane fly");
    }
}
