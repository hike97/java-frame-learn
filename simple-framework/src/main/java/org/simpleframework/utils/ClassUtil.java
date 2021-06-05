package org.simpleframework.utils;

import java.util.Set;

/**
 * @author hike97
 * @create 2021-06-05 23:34
 * @desc 根据传的包名获取包下类集合
 **/
public class ClassUtil {
    /**
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass (String packageName) {
        //1.获取到类的加载器
        //2.通过类加载器获取到加载的资源
        //3.依据不同的资源类型，采用不同的方式获取资源的集合
        return null;
    }

    /**
     * 获取ClassLoader
     *
     * @return 当前classLoader
     */
    public static ClassLoader getClassLoader () {
        return Thread.currentThread ().getContextClassLoader ();
    }
}
