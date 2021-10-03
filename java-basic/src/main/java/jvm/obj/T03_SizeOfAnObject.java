package jvm.obj;

//import com.mashibing.jvm.agent.ObjectSizeAgent;

/**
 * @author hike97
 * @create 2021-08-18 20:38
 * @desc 测试object大小
 * <p>
 * -javaagent:E:\idea_workspace_2021\ObjectSize.jar
 **/
public class T03_SizeOfAnObject {

//    public static void main (String[] args) {
//        System.out.println (ObjectSizeAgent.sizeOf (new Object ()));
//        System.out.println (ObjectSizeAgent.sizeOf (new int[]{}));
//        System.out.println (ObjectSizeAgent.sizeOf (new P ()));
//    }
//
//    //一个Object占多少个字节
//    // -XX:+UseCompressedClassPointers -XX:+UseCompressedOops
//    // Oops = ordinary object pointers
//    private static class P {
//        //8 _markword
//        //4 _oop指针
//        //        int id;         //4
//        //        String name;    //4 引用类型 -XX:+UseCompressedOops 压缩完 4个
//        //        int age;        //4
//        //
//        //        byte b1;        //1
//        //        byte b2;        //1
//        //
//        //        Object o;       //4
//        //        byte b3;        //1
//        long l;         //8
//        double d;       //8
//        float f;       //4
//        char c;       //1
//        short s;       //2
//
//    }
}
