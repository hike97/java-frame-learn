package designpattern.factory.abstractfactory;

/**
 * @author hike97 2month
 * @create 2021-05-09 18:15
 * @desc 现代人一族工厂 开车 吃面包 拿ak
 **/
public class ModernFactory extends AbstractFactory{
    @Override
    Food createFood () {
        return new Bread ();
    }

    @Override
    Vehicle createVehicle () {
        return new Car ();
    }

    @Override
    Weapon createWeapon () {
        return new AK47 ();
    }
}
