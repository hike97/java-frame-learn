package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 15:04
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.CLASS)
public @interface TestAnnotation {

    String getString () default "ss";

    /**
     * 为了探究自定义annotation 和 annotation接口的关系 操作如下
     * 1.编译当前注解声称class文件
     * javac TestAnnotation.java
     * 2.反编译当前class
     * javap -verbose TestAnnotation.class > TestAnnotation.txt
     * 发现：public interface annotation.TestAnnotation extends java.lang.annotation.Annotation
     * 证实 自定义annotation时Annotation接口的子类
     */

}
