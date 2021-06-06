package org.simpleframework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hike97
 * @create 2021-06-05 23:34
 * @desc 根据传的包名获取包下类集合
 **/
@Slf4j
public class ClassUtil {
    //统一资源定位符 以file开头的实例
    public static final String FILE_PROTOCOL = "file";

    /**
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass (String packageName) {
        //1.获取到类的加载器
        ClassLoader classLoader = getClassLoader ();
        //2.通过类加载器获取到加载的资源
        URL url = classLoader.getResource (packageName.replace (".", "/"));
        if (url == null) {
            log.warn ("找不到相应包：{}", packageName);
            return null;
        }
        //3.依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        if (url.getProtocol ().equalsIgnoreCase (FILE_PROTOCOL)) {
            classSet = new HashSet<Class<?>> ();
            //过滤出文件类型的资源
            File packageDirectory = new File (url.getPath ());
            extractClassFile (classSet, packageDirectory, packageName);

        }
        //TODO 此处可以加入针对其他类型资源的处理 例如jar
        return classSet;
    }

    /**
     * 获取ClassLoader
     * 关键方法：ClassLoader.getResource() 返回：URL file://协议的资源
     *
     * @return 当前classLoader
     */
    public static ClassLoader getClassLoader () {
        return Thread.currentThread ().getContextClassLoader ();
    }

    /**
     * 加载类
     *
     * @param className 包名+类名
     * @return
     */
    public static Class<?> loadClass (String className) {
        try {
            return Class.forName (className);
        } catch (ClassNotFoundException e) {
            log.error ("load class error:{}", e.getMessage ());
            throw new RuntimeException (e);
        }
    }


    private static void extractClassFile (Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory ()) {
            return;
        }
        //如果是一个文件夹 则调用listFiles方法获取文件夹下的文件或文件夹
        File[] files = fileSource.listFiles (new FileFilter () {
            @Override
            public boolean accept (File file) {
                if (file.isDirectory ()) {
                    return true;
                } else {
                    // 获取文件的绝对路径
                    String absoluteFilePath = file.getAbsolutePath ();
                    if (absoluteFilePath.endsWith (".class")) {
                        addToClassSet (absoluteFilePath);
                    }
                }
                return false;
            }

            /**
             * 根据class的绝对路径获取并生成class对象，并放入classSet中
             */
            private void addToClassSet (String absoluteFilePath) {
                //获取全路径类名将/替换成. E:\idea_workspace_2021\java-learn-2021\simple-framework\target\classes\com.simple.entity\dto\Result.class
                absoluteFilePath = absoluteFilePath.replace (File.separator, ".");
                //将全路径名截成包名 com.simple.entity.dto.Result.class
                String className = absoluteFilePath.substring (absoluteFilePath.indexOf (packageName));
                //将class 截掉 com.simple.entity.dto.Result
                className = className.substring (0, className.lastIndexOf ("."));
                // 通过反射机制获取对应的class对象并加入到classSet中 class.forName("com.simple.entity.dto.Result")
                Class targetClass = loadClass (className);
                emptyClassSet.add (targetClass);
            }

        });
        if (files != null) {
            //forEach 方法需要空值判断
            for (File file : files) {
                //递归调用
                extractClassFile (emptyClassSet, file, packageName);
            }
        }
    }

    /**
     * 实例化class
     *
     * @param clazz         class类型
     * @param accessible    是否创建出私有class对象的实例
     * @param <T>annotation
     * @return
     */
    public static <T> T newInstance (Class<?> clazz, boolean accessible) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor ();
            constructor.setAccessible (accessible);
            return (T) constructor.newInstance ();
        } catch (Exception e) {
            log.warn ("创建实例失败：{}", e.getMessage ());
            throw new RuntimeException (e);
        }
    }

    /**
     * 设置类的属性值
     *
     * @param field      属性（成员变量）
     * @param target     目标类
     * @param value      设置值
     * @param accessible 是否对私有变量赋值
     */
    public static void setField (Field field, Object target, Object value, boolean accessible) {
        field.setAccessible (accessible);
        try {
            field.set (target, value);
        } catch (IllegalAccessException e) {
            log.error ("setField error", e);
            throw new RuntimeException (e);
        }
    }


    public static void main (String[] args) {
        extractPackageClass ("com/simple/entity");
    }
}
