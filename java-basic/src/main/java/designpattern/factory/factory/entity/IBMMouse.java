package designpattern.factory.factory.entity;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class IBMMouse implements Mouse {

    @Override
    public void sayHi() {
        System.out.println("我是lenovo旗下的IBM鼠标");
    }
}