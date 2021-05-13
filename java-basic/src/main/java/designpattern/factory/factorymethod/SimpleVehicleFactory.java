package designpattern.factory.factorymethod;

/**
 * @author hike97 2month
 * @create 2021-05-08 19:52
 * @desc 简单工厂 可拓展性不好
 **/
public class SimpleVehicleFactory {

    public Car createCar() {
        return new Car ();
    }

    public Broom createBroom() {
        return new Broom ();
    }
}
