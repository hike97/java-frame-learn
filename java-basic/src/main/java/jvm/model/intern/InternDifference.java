package jvm.model.intern;

public class InternDifference {
    public static void main (String[] args) {
        String s = new String ("a");
        s.intern ();
        String s2 = "a";
        System.out.println (s == s2);

        String s3 = new String ("a") + new String ("a");
        //此时a已经在字符串常量池存在 不会新建 s3 会产生"aa"
        // 这样 “aa” 在堆中的实例早于常量池中 intern会将"aa"的reference 放入字符串常量池
        s3.intern ();
        String s4 = "aa";
        System.out.println (s3 == s4);
    }
}
