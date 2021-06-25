package reflect;

import lombok.SneakyThrows;

import java.io.*;

/**
 * @author hike97
 * @create 2021-06-09 14:12
 * @desc 自定义classloader
 **/
public class MyClassLoader extends ClassLoader {
    private String path;
    private String classLoaderName;

    public MyClassLoader (String path, String classLoaderName) {
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    /**
     * 用于寻找类文件
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass (String name) throws ClassNotFoundException {
        byte[] b = loadClassData (name);
        return defineClass (name, b, 0, b.length);
    }

    /**
     * 用于加载类文件
     *
     * @param name
     * @return
     */
    private byte[] loadClassData (String name) {
        name = path + name + ".class";
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream (new File (name));
            out = new ByteArrayOutputStream ();
            int i = 0;
            while ((i = in.read ()) != -1) {
                out.write (i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            try {
                out.close ();
                in.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        return out.toByteArray ();
    }

    @SneakyThrows
    public static void main (String[] args) {
        MyClassLoader loader = new MyClassLoader ("d:/", "Wali");
        Class<?> wali = loader.loadClass ("Wali");
        System.out.println (wali.getClassLoader ());
        wali.getDeclaredConstructor ().newInstance ();
    }
}
