package reflect.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hike97
 * @create 2021-06-16 17:37
 * @desc 切面
 **/
public interface Aspect {
    void before ();

    void after ();

    /**
     * 在接口中写静态方法
     */
    static <T> T getProxy (Class<T> cls, String... aspects) throws Exception {
        //根据传过来的值获取切面实例
        var aspectInstances = Arrays.stream (aspects).map (name -> {
            try {
                Class<?> clazz = Class.forName (name);
                if (Objects.isNull (clazz)) {
                    return null;
                } else {
                    return (Aspect) clazz.getConstructor ().newInstance ();
                }
            } catch (Exception e) {
                e.printStackTrace ();
                return null;
            }
        }).filter (x -> !Objects.isNull (x)).collect (Collectors.toList ());

        //也可以写成monad的形式 后者更具观赏性 但需要导包
        //See:url: https://github.com/jasongoodwin/better-java-monads/blob/master/src/main/java/com/jasongoodwin/monads/Try.java
        //       var aspectInstances =  Arrays.stream (aspects).map (name-> Try.ofFailable (()->{
        //            var clazz = Class.forName (name);
        //            return (Aspect)clazz.getConstructor ().newInstance ();
        //        })).filter (aspect->aspect.isSuccess ()).collect (Collectors.toList ());
        //JDKDynamicProxy
        //获取代理类的实例
        T inst = cls.getConstructor ().newInstance ();
        return (T) Proxy.newProxyInstance (cls.getClassLoader (), cls.getInterfaces (), (proxy, method, args) -> {
            for (var instance : aspectInstances) {
                instance.before ();
            }
            var result = method.invoke (inst);
            for (var instance : aspectInstances) {
                instance.after ();
            }
            return result;
        });
    }

    /**
     * 用注解的方式 实现aop
     *
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    static <T> T newInstance (Class<T> cls) throws Exception {
        //根据传过来的值获取切面实例
        Annotation[] annotations = cls.getAnnotations ();
        LinkedList<Aspect> aspects = new LinkedList<> ();
        for (Annotation annotation : annotations) {
            if (annotation instanceof _Aspect) {
                Class aspectType = ((_Aspect) annotation).type ();
                Aspect aspect = (Aspect) aspectType.getConstructor ().newInstance ();
                aspects.add (aspect);
            }
        }
        //获取代理类的实例
        T inst = cls.getConstructor ().newInstance ();
        return (T) Proxy.newProxyInstance (cls.getClassLoader (), cls.getInterfaces (), (proxy, method, args) -> {
            aspects.forEach (a -> a.before ());
            var result = method.invoke (inst);
            aspects.forEach (a -> a.after ());
            return result;
        });
    }
}
