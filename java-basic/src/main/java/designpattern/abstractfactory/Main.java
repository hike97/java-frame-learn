package designpattern.abstractfactory;

import designpattern.factorymethod.Broom;
import designpattern.factorymethod.Moveable;

/**
 * @author hike97 2month
 * @create 2021-05-08 19:40
 * @desc
 * 1.产品族时 用抽象工厂易于扩展
 *  AbstractFactory 产品维度扩展
 * 2.但是如果新增某个产品 用简单工厂 更易于扩展
 *  Factory Method  产品一族进行扩展
 * 3.更好的解决方案 spring ioc bean工厂
 **/
public class Main {
    public static void main (String[] args) {
//        AbstractFactory factory = new ModernFactory ();
        AbstractFactory factory = new MagicFactory ();
        Food food = factory.createFood ();
        food.printName ();
        Weapon weapon = factory.createWeapon ();
        weapon.shoot ();
        Vehicle vehicle = factory.createVehicle ();
        vehicle.go ();
    }
}
