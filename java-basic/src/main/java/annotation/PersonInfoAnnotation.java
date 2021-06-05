package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 15:27
 */
//作用到类的成员变量
@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
public @interface PersonInfoAnnotation {
    //名字 protected private 都不行
    //只能安方法定义
    String name () default "name";

    int age () default 20;

    String gender ();

    String[] language ();
}
