package designpattern.abstractfactory;

/**
 * @author hike97 2month
 * @create 2021-05-09 18:08
 * @desc 抽象工厂
 **/
public abstract class AbstractFactory {
    abstract Food createFood();
    abstract Vehicle createVehicle();
    abstract Weapon createWeapon();
}
