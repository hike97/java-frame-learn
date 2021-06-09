package designpattern.factory.factory.abstractf;


import designpattern.factory.factory.entity.KeyBoard;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
/*抽象工厂模式：提供一个创建一系列相关或相互依赖对象的接口，而无需指定他们具体的类
    优点：支持多类产品的创建
    缺点：违反开闭原则、增加系统复杂度
* */
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        ComputerFactory hpFactory = new HpComputerFactory();
        Mouse hpMouse = hpFactory.createMouse();
        KeyBoard hpKeyBoard = hpFactory.createKeyBoard();
        hpMouse.sayHi();
        hpKeyBoard.sayHello();
    }
}