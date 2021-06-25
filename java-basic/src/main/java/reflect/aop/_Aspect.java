package reflect.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hike97
 * @create 2021-06-16 19:30
 * @desc Aspect Annotation
 **/
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface _Aspect {
    Class type ();
}
