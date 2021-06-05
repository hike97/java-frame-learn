package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 解析注解
 */
public class AnnotationParser {

    public static void parseTypeAnnotation () throws ClassNotFoundException {
        Class clazz = Class.forName ("annotation.ImoocCourse");

        // 获取类上注释
        Annotation[] annotations = clazz.getAnnotations ();
        for (Annotation annotation : annotations) {
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
            System.out.println (annotation);
            System.out.println (courseInfoAnnotation.courseName ());
        }
    }

    public static void parseFieldAnnotation () throws ClassNotFoundException {
        Class clazz = Class.forName ("annotation.ImoocCourse");
        Field[] fields = clazz.getDeclaredFields ();
        for (Field field : fields) {
            System.out.println (field);
            // 判断成员变量中是否有指定类型的注释
            Boolean hasPersionAnno = field.isAnnotationPresent (PersonInfoAnnotation.class);
            if (hasPersionAnno) {
                PersonInfoAnnotation personInfoAnnotation = field.getAnnotation (PersonInfoAnnotation.class);
                System.out.println (personInfoAnnotation.name () + "\n" + personInfoAnnotation.gender ());
            }
        }
    }


    public static void main (String[] args) throws ClassNotFoundException {
        //        System.out.println ("####################获取类的注解#####################################");
        //        parseTypeAnnotation();
        System.out.println ("####################获取属性的注解###################################");
        parseFieldAnnotation ();
    }
}
