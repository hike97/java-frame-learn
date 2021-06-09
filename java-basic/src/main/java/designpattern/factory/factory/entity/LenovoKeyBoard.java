package designpattern.factory.factory.entity;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class LenovoKeyBoard implements KeyBoard {

    @Override
    public void sayHello() {
        System.out.println("我是联想键盘");
    }
}