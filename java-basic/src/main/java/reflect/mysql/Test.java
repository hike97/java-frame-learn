package reflect.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Test {
    public static void main (String[] args) throws Exception {
        Class.forName ("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/sakila?characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        Connection conn = DriverManager.getConnection (url, username, password);
        PreparedStatement pstmt = conn.prepareStatement ("insert into psn2 values(?,?)");
        for (int i = 0; i < 20000; i++) {
            pstmt.setInt (1, i);
            pstmt.setString (2, i + "");
            pstmt.addBatch ();
        }
        pstmt.executeBatch ();
        conn.close ();
    }
}
