package designpattern.chainofresponsibility.servlet.v2;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-09 22:50
 * @desc  模拟 servlet 不带布尔返回值
 **/
public class Main {

    public static void main (String[] args) {
        Request request = new Request ();
        request.setMsg ("request");
        Response response = new Response ();
        response.setMsg ("response");

        FilterChain chain = new FilterChain ();
        chain.add (new HTMLFilter ()).add (new EmojiFilter ());
        chain.doFilter (request,response,chain);
        System.out.println (request.getMsg ());
        System.out.println (response.getMsg ());
    }
}

@Data
class Request {
    private String msg;
}

@Data
class Response {
    private String msg;
}

interface Filter {
    /**
     * 进行过滤器过滤
     * @param request
     * @param response
     * @return
     */
    void doFilter(Request request,Response response,FilterChain chain);
}

class HTMLFilter implements Filter {
    @Override
    public void doFilter (Request request, Response response, FilterChain chain) {
        request.setMsg (request.getMsg ()+" ->->HTMLFilter()");
        chain.doFilter (request,response,chain);
        response.setMsg (response.getMsg ()+" <-<-HTMLFilter()");
    }
}

class EmojiFilter implements Filter {
    @Override
    public void doFilter (Request request,Response response,FilterChain chain) {
        request.setMsg (request.getMsg ()+" ->->EmojiFilter()");
        chain.doFilter (request,response,chain);
        response.setMsg (response.getMsg ()+" <-<-EmojiFilter()");
    }
}

class FilterChain {
    List<Filter>  filters = new ArrayList<>();
    int index = 0;

    public FilterChain add(Filter f){
        filters.add (f);
        return this;
    }

    public void doFilter(Request request,Response response,FilterChain chain){
        if (index == filters.size () ) {
            return ;
        }
        Filter f = filters.get (index);
        index ++;
        f.doFilter (request,response,chain);
    }
}