package designpattern.factory.factorymethod;

/**
 * @author hike97 2month
 * @create 2021-05-08 19:55
 * @desc Car工厂
 **/
public class CarFactory {

    public Car createCar(){
        System.out.println ("a new Car is producing");
        return new Car ();
    }
}
