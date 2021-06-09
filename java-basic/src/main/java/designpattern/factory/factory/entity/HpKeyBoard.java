package designpattern.factory.factory.entity;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class HpKeyBoard implements KeyBoard {

    @Override
    public void sayHello() {
        System.out.println("我是惠普键盘");
    }
}