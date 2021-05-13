package designpattern.factory.abstractfactory;

/**
 * @author hike97 2month
 * @create 2021-05-09 18:15
 * @desc 魔法师一族 吃蘑菇 骑扫帚 用法棍
 **/
public class MagicFactory extends AbstractFactory{
    @Override
    Food createFood () {
        return new MashRoom ();
    }

    @Override
    Vehicle createVehicle () {
        return new Broom ();
    }

    @Override
    Weapon createWeapon () {
        return new MagicStick();
    }
}
