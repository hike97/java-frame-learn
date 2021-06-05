package reflect;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 13:54
 */
public class RefectTarget extends ReflectTargetOrigin {
    public String name;
    protected int index;

    private String targetInfo;
    char type;


    RefectTarget (char str) {
        System.out.println ("str--char = " + str);
    }

    public RefectTarget () {
        System.out.println ("default");
    }

    public RefectTarget (String str) {
        System.out.println ("str = " + str);
    }

    public RefectTarget (String name, int index) {
        System.out.println ("name = " + name + ", index = " + index);
    }

    protected RefectTarget (Boolean b) {
        System.out.println ("b = " + b);
    }

    private RefectTarget (int index) {
        System.out.println ("index 私有构造方法 = " + index);
    }


    public void show1 (String str) {
        System.out.println ("str = " + str);
    }

    protected void show2 () {
        System.out.println ("show2");
    }

    void show3 () {
        System.out.println ("show4");
    }

    private int show4 (int index) {
        System.out.println ("index = " + index);
        return index;
    }

    @Override
    public String toString () {
        return "ReflectTarget{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", targetInfo='" + targetInfo + '\'' +
                ", type=" + type +
                '}';
    }

    public static void main (String[] args) throws ClassNotFoundException {
        RefectTarget reflectTarget = new RefectTarget ();

        // 获取class
        System.out.println (reflectTarget.getClass ().getName ());
        System.out.println (RefectTarget.class.getName ());
        System.out.println (Class.forName ("reflect.RefectTarget").getName ());
    }

}
