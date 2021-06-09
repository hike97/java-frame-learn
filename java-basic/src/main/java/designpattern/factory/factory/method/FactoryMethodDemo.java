package designpattern.factory.factory.method;


import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
/*工厂方法模式
    优点：遵循开闭原则、单一职责原则、对客户端隐藏对象的实现细节
    缺点：1.只支持同一类产品的创建  2.改动成本较大、不适合进行扩展
* */
public class FactoryMethodDemo {
    public static void main(String[] args) {
        HpMouseFactory hpMouseFactory = new HpMouseFactory();
        Mouse mouse = hpMouseFactory.createMouse();
        mouse.sayHi();
    }
}