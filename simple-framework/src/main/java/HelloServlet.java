import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hike97
 * @create 2021-06-04 23:17
 * @desc hello servlet
 **/
@WebServlet ("/hello")
@Slf4j
public class HelloServlet extends HttpServlet {

    //    Logger log = LoggerFactory.getLogger (HelloServlet.class);

    @Override
    public void init () throws ServletException {
        super.init ();
        System.out.println ("init:init方法 只能执行一次");
    }

    @Override
    protected void service (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println ("执行了初始化方法 我才是入口");
        doGet (req, resp);
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "我的第一个简单框架";
        req.setAttribute ("name", name);
        log.debug ("name:" + name);
        req.getRequestDispatcher ("WEB-INF/jsp/hello.jsp").forward (req, resp);
    }

    /**
     * 容器关闭执行destory
     */
    @Override
    public void destroy () {
        super.destroy ();
        System.out.println ("执行销毁方法");
    }
}
