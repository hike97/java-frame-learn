package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 14:24
 */
public class FieldCollector {
    public static void main (String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName ("cn.airfei.demo.reflect.RefectTarget");

        System.out.println ("*********获取所有公有成员变量××××××××××××××××××××");
        Field[] fields = clazz.getFields ();
        for (Field field : fields) {
            System.out.println (field);
        }

        System.out.println ("*********获取所有成员变量××××××××××××××××××××");
        fields = clazz.getDeclaredFields ();
        for (Field field : fields) {
            System.out.println (field);
        }

        System.out.println ("*********获取所有单个公有成员变量××××××××××××××××××××");
        Field field = clazz.getField ("name");
        System.out.println (field);
        RefectTarget reflectTarget = (RefectTarget) clazz.getConstructor ().newInstance ();
        field.set (reflectTarget, "hahhahahah");
        System.out.println ("验证name，：" + reflectTarget.name);


        System.out.println ("*********获取所有单个任意成员变量××××××××××××××××××××");
        field = clazz.getDeclaredField ("targetInfo");
        field.setAccessible (true);
        field.set (reflectTarget, "私有targetInfo");
        System.out.println ("验证私有targetInfo，：" + reflectTarget);
    }

}
