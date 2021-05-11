package designpattern.chainofresponsibility.servlet.v1;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-09 22:50
 * @desc  模拟 servlet
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
    boolean doFilter(Request request,Response response,FilterChain chain);
}

class HTMLFilter implements Filter {
    @Override
    public boolean doFilter (Request request,Response response,FilterChain chain) {
        request.setMsg (request.getMsg ()+" ->->HTMLFilter()");
        chain.doFilter (request,response,chain);
        response.setMsg (response.getMsg ()+" <-<-HTMLFilter()");
        return true;
    }
}

class EmojiFilter implements Filter {
    @Override
    public boolean doFilter (Request request,Response response,FilterChain chain) {
        request.setMsg (request.getMsg ()+" ->->EmojiFilter()");
        chain.doFilter (request,response,chain);
        response.setMsg (response.getMsg ()+" <-<-EmojiFilter()");
        return true;
    }
}

class FilterChain implements Filter{
    List<Filter>  filters = new ArrayList<>();
    int index = 0;

    public FilterChain add(Filter f){
        filters.add (f);
        return this;
    }

    @Override
    public boolean doFilter(Request request,Response response,FilterChain chain){
        if (index == filters.size () ) {
            return false;
        }
        Filter f = filters.get (index);
        index ++;
        return f.doFilter (request,response,chain);
    }
}