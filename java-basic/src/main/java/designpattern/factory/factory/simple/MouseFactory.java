package designpattern.factory.factory.simple;


import designpattern.factory.factory.entity.DellMouse;
import designpattern.factory.factory.entity.HpMouse;
import designpattern.factory.factory.entity.LenovoMouse;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
/*简单工厂使用场景：
    1、需要创建的对象较少
    2.客户端不关心对象的创建过程
  优点：对客户端隐藏相关细节
  缺点：1.因创建逻辑复杂或创建对象过多导致代码臃肿   2.新增、删除子类均违反开闭原则
  开闭原则：一个软件实体（类、模块、方法），应该对扩展开放，对修改关闭
* */
public class MouseFactory {
    public static Mouse createMouse(int type){
        switch (type){
            case 0: return new DellMouse ();
            case 1: return new HpMouse ();
            case 2: return new LenovoMouse ();//违反开闭原则
            default: return new DellMouse();
        }
    }

    public static void main(String[] args) {
        Mouse mouse = MouseFactory.createMouse(1);
        mouse.sayHi();
    }
}