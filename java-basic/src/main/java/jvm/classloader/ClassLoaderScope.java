package jvm.classloader;

/**
 * @author hike97
 * @create 2021-08-11 18:33
 * @desc
 **/
public class ClassLoaderScope {
    public static void main (String[] args) {
        String pathBoot = System.getProperty ("java.class.path");
        System.out.println (pathBoot.replaceAll (";", System.lineSeparator ()));
    }
}
