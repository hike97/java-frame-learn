package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: air
 * @create: 2020-03-14 15:30
 */
@Target ({ElementType.TYPE, ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface CourseInfoAnnotation {
    String courseName ();

    public String courseTag ();

    public int courseIndex () default 11;

    String courseProfile () default "牛比";
}
