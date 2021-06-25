package reflect;

import java.lang.reflect.InvocationTargetException;

public class LoadDifference {
    public static void main (String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassLoader cl = Robot.class.getClassLoader ();
        //        Class r = Class.forName("reflect.Robot");
        Class<?> robot = cl.loadClass ("reflect.Robot");
        robot.getDeclaredConstructor ().newInstance ();
        // 打开相关代码会看到static 代码块
        // static {
        //        try {
        //            java.sql.DriverManager.registerDriver(new Driver());
        //        } catch (SQLException E) {
        //            throw new RuntimeException("Can't register driver!");
        //        }
        //    }
        //        Class.forName("com.mysql.cj.jdbc.Driver");
    }
}
