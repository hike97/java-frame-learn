package jvm.classloader;

/**
 * @author hike97
 * @create 2021-08-12 12:58
 * @desc
 **/
public class ClassLoadingProcedure {
    /**
     * Linking 的三个阶段
     * 1.Verification
     * 验证文件是否符合jvm规定
     * 2.Preparation
     * 静态成员变量赋默认值
     * 3.Resolution
     * 将类方法属性等符号引用解析为直接引用
     * 常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用
     *
     * @param args
     */
    public static void main (String[] args) {
        System.out.println (T.count);
    }
}

/**
 * T.count 时 需要T类的装载
 * Linking阶段
 * 1.Verification 略
 * 2.Preparation阶段 赋默认值
 * t = null
 * count = 0
 * 3.resolution阶段
 * 执行new t  ->count ++ =>count = 1
 * int count = 2
 * 所以最终count为2
 * <p>
 * 如果将两行代码顺序调换
 * int count = 2
 * 执行new t  ->count ++ =>count = 3
 * 所以最终count为 3
 */
class T {
    public static T t = new T ();
    public static int count = 2;

    //    public  static T t = new T ();
    private T () {
        count++;
    }
}
