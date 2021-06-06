package org.simpleframework.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.BeanContainer;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.utils.ClassUtil;
import org.simpleframework.utils.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author hike97
 * @create 2021-06-06 20:01
 * @desc ioc操作
 **/
@Slf4j
public class DependencyInject {
    private BeanContainer beanContainer;

    /**
     * 将bean容器注入到DI类中
     */
    public DependencyInject () {
        this.beanContainer = BeanContainer.getInstance ();
    }

    /**
     * 执行ioc
     */
    public void doIOC () {

        if (ValidationUtil.isEmpty (beanContainer.getClasses ())) {
            log.warn ("容器为空...");
            return;
        }
        //1.遍历Bean容器中所有的Class对象
        for (Class<?> clazz : beanContainer.getClasses ()) {
            //2.遍历Class对象的所有成员变量
            Field[] files = clazz.getDeclaredFields ();
            if (ValidationUtil.isEmpty (files)) {
                continue;
            }
            for (Field field : files) {
                //3.找出被Autowired标记的成员变量
                if (field.isAnnotationPresent (Autowired.class)) {
                    /*
                       pak:为了防止一个接口有多个实例无法区分将Autowired添加成员变量value() 对标 @Qualifier
                     */
                    Autowired autowired = field.getAnnotation (Autowired.class);
                    String autowiredValue = autowired.value ();
                    // 4.获取成员变量的类型
                    Class<?> fieldClass = field.getType ();
                    // 5.获取成员变量的类型在容器对应的实例
                    Object fieldValue = getFieldInstance (fieldClass, autowiredValue);
                    if (fieldValue == null) {
                        throw new RuntimeException ("unable to inject relevant type ,targetClass is" + fieldClass.getName ());
                    } else {
                        //6.通过反射将对应的成员变量实例注入到成员变量所在类的实例里
                        Object targetBean = beanContainer.getBean (clazz);
                        ClassUtil.setField (field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    /**
     * 根据class在beanContainer里获取其实例或实现类
     *
     * @param fieldClass
     * @return
     */
    private Object getFieldInstance (Class<?> fieldClass, String autowiredValue) {
        //尝试获取当前类的实现类
        Object fieldValue = beanContainer.getBean (fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            //如果实现类为空 则获取其接口的实现类的对象
            Class<?> implementedClass = getImplementedClass (fieldClass, autowiredValue);
            if (implementedClass != null) {
                //获取类的实例
                return beanContainer.getBean (implementedClass);
            } else {
                return null;
            }
        }
    }

    /**
     * 获取接口的实现类
     *
     * @param fieldClass
     * @param autowiredValue
     * @return
     */
    private Class<?> getImplementedClass (Class<?> fieldClass, String autowiredValue) {
        //不包括接口本身
        Set<Class<?>> classSet = beanContainer.getClassesBySuper (fieldClass);
        if (!ValidationUtil.isEmpty (classSet)) {
            if (ValidationUtil.isEmpty (autowiredValue)) {
                if (classSet.size () == 1) {
                    return classSet.iterator ().next ();
                } else {
                    throw new RuntimeException ("接口存在多个实现类，您需要指定@Autowired(value) 具体类：" + fieldClass.getName ());
                }
            } else {
                //多个实现类根据value进行筛选
                for (Class<?> c : classSet) {
                    if (autowiredValue.equals (c.getSimpleName ())) {
                        return c;
                    }
                }
            }
        }
        return null;
    }


}
