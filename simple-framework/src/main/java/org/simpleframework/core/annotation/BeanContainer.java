package org.simpleframework.core.annotation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.utils.ClassUtil;
import org.simpleframework.utils.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hike97
 * @create 2021-06-06 13:33
 * @desc 单例容器载体
 **/
@Slf4j
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class BeanContainer {

    // 容器是否被加载过
    private boolean loading = false;

    // 存放所有被配置标记的目标对象的map
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap ();

    /**
     * 是否是标记的四个注解
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList (Component.class, Controller.class, Service.class, Repository.class);

    /**
     * 获取Bean容器实例
     *
     * @return
     */
    public static BeanContainer getInstance () {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder () {
            instance = new BeanContainer ();
        }
    }

    /**
     * 判断是否被加载
     *
     * @return
     */
    public boolean isLoading () {
        return loading;
    }

    /**
     * 返回beanMap的大小
     *
     * @return
     */
    public int size () {
        return beanMap.size ();
    }

    /**
     * 装载bean的实例
     *
     * @param packageName
     */
    public synchronized void loadBeans (String packageName) {
        if (loading) {
            log.warn ("容器已经被加载过");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass (packageName);
        if (ValidationUtil.isEmpty (classSet)) {
            log.warn ("没有找到相应的包:{}", packageName);
            return;
        }
        for (Class c : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                //如果发现bean被注解标注
                if (c.isAnnotationPresent (annotation)) {
                    //将每发现的类进行实例化
                    beanMap.put (c, ClassUtil.newInstance (c, true));
                }
            }
        }
        loading = true;
    }

    /**
     * 添加一个class对象及其bean的实例
     *
     * @param clazz
     * @param bean
     * @return
     */
    public Object addBean (Class<?> clazz, Object bean) {
        return beanMap.put (clazz, bean);
    }

    /**
     * 移除一个ioc容器管理的对象
     *
     * @param clazz
     * @return
     */
    public Object removeBean (Class<?> clazz) {
        return beanMap.remove (clazz);
    }

    /**
     * 获取一个bean实例
     *
     * @param clazz
     * @return
     */
    public Object getBean (Class<?> clazz) {
        return beanMap.get (clazz);
    }

    /**
     * 获取容器管理的所有class对象集合
     *
     * @return
     */
    public Set<Class<?>> getClasses () {
        return beanMap.keySet ();
    }

    /**
     * 获取所有bean的集合
     *
     * @return
     */
    public Set<Object> getBeans () {
        return new HashSet<Object> (beanMap.values ());
    }

    /**
     * 根据注释筛选bean的class集合
     *
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation (Class<? extends Annotation> annotation) {
        Set<Class<?>> keySet = getClasses ();
        if (ValidationUtil.isEmpty (keySet)) {
            log.warn ("没有任何class对象");
            return null;
        }
        Set<Class<?>> classSet = new HashSet<> ();
        for (Class<?> c : keySet) {
            if (c.isAnnotationPresent (annotation)) {
                classSet.add (c);
            }
        }
        return classSet.size () > 0 ? classSet : null;
    }


    /**
     * 通过接口或父类或获取实现类或子类的集合，不包括其本身
     *
     * @param interfaceOrClass
     * @return
     */
    public Set<Class<?>> getClassesBySuper (Class<?> interfaceOrClass) {
        Set<Class<?>> keySet = getClasses ();
        if (ValidationUtil.isEmpty (keySet)) {
            log.warn ("没有任何class对象");
            return null;
        }

        Set<Class<?>> classSet = new HashSet<> ();
        for (Class<?> c : keySet) {
            // isAssignableFrom() 判断keySet里的元素是否是传入的接口或类的子类
            if (interfaceOrClass.isAssignableFrom (c) && !c.equals (interfaceOrClass)) {
                classSet.add (c);
            }
        }
        return classSet.size () > 0 ? classSet : null;
    }
}
