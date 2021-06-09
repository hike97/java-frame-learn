package designpattern.template;

/**
 * 父类定制好的方法m()
 * 具体实现由子类按照父类的模板执行
 * 又称 钩子函数 回调函数
 */
public class Main {
    public static void main(String[] args) {
        F f = new C1();
        f.m();
    }

}

abstract class F {
    public void m() {
        op1();
        op2();
    }

    abstract void op1();
    abstract void op2();
}

class C1 extends F {

    @Override
    void op1() {
        System.out.println("op1");
    }

    @Override
    void op2() {
        System.out.println("op2");
    }
}
