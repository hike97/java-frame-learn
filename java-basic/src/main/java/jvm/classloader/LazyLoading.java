package jvm.classloader;

/**
 * @author hike97
 * @create 2021-08-11 19:58
 * @desc 关于懒加载的示例
 **/
public class LazyLoading { //严格来讲应该叫 lazy initializing
    public static void main (String[] args) throws ClassNotFoundException {
        //        P p;
        //new 子类对象 父类必须先加载
        //        X x = new X ();
        //final 属性 不需要初始化类
        //        System.out.println (P.i);
        //静态属性 需要初始化类
        //        System.out.println (P.j);
        //反射需要初始化类
        Class.forName ("jvm.classloader.LazyLoading$P");

    }

    public static class P {
        final static int i = 8;
        static int j = 9;

        static {
            System.out.println ("P 进行了加载");
        }
    }

    public static class X extends P {
        static {
            System.out.println ("X 进行了加载");
        }
    }
}
