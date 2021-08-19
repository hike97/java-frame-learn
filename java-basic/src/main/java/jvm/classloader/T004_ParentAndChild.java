package jvm.classloader;

/**
 * @author hike97
 * @create 2021-08-11 18:24
 * @desc ��
 **/
public class T004_ParentAndChild {
    public static void main (String[] args) {
        System.out.println (T004_ParentAndChild.class.getClassLoader ());
        System.out.println (T004_ParentAndChild.class.getClassLoader ().getClass ().getClassLoader ());
        System.out.println (T004_ParentAndChild.class.getClassLoader ().getParent ());
        System.out.println (T004_ParentAndChild.class.getClassLoader ().getParent ().getParent ());
    }
}
