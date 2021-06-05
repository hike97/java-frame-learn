package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hike97
 * @create 2021-06-05 15:57
 * @desc tomcat 配置如下:
 * <!-- The mapping for the default servlet -->
 * <servlet-mapping>
 * <servlet-name>default</servlet-name>
 * <url-pattern>/</url-pattern>
 * </servlet-mapping>
 * <p>
 * <!-- The mappings for the JSP servlet -->
 * <servlet-mapping>
 * <servlet-name>jsp</servlet-name>
 * <url-pattern>*.jsp</url-pattern>
 * <url-pattern>*.jspx</url-pattern>
 * </servlet-mapping>
 */
// 反斜杠 / 和 /* 不同 /* 会拦截所有请求 包括jsp页面

/**
 * tomcat 配置如下:
 *  <!-- The mapping for the default servlet -->
 *     <servlet-mapping>
 *         <servlet-name>default</servlet-name>
 *         <url-pattern>/</url-pattern>
 *     </servlet-mapping>
 *
 *     <!-- The mappings for the JSP servlet -->
 *     <servlet-mapping>
 *         <servlet-name>jsp</servlet-name>
 *         <url-pattern>*.jsp</url-pattern>
 *         <url-pattern>*.jspx</url-pattern>
 *     </servlet-mapping>
 */

/**
 * / 优先级最低 不会覆盖url
 * /hello 高于 /*
 * /* 属于路径匹配 优先级高于 *.jsp
 */
@WebServlet ("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println ("path = " + req.getServletPath ());
        System.out.println ("method type= " + req.getMethod ());
        super.service (req, resp);
    }
}
