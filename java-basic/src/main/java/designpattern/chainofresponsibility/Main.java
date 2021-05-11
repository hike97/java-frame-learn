package designpattern.chainofresponsibility;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-09 22:50
 * @desc 责任链模式
 **/
public class Main {

    public static void main (String[] args) {
        Msg msg = new Msg ();
        msg.setMsg ("大家好(￣▽￣),<script>,欢迎访问 91porn.com,大家都是996~");
        FilterChain chain = new FilterChain ();
        chain.add (new HTMLFilter ()).add (new SensitiveFilter ());
        //另外一个filterChain
        FilterChain chain1 = new FilterChain ();
        chain1.add (new EmojiFilter ()).add (new UrlFilter ());
        chain.add (chain1);
        chain.doFilter (msg);
        System.out.println (msg);
    }
}
@Data
class Msg {
    private String name;
    private String msg;
}

interface Filter {
    boolean doFilter(Msg m);
}

class HTMLFilter implements Filter {
    @Override
    public boolean doFilter (Msg m) {
        String msg = m.getMsg ();
        msg = msg.replace ('<','[');
        msg = msg.replace ('>',']');
        m.setMsg (msg);
        return true;
    }
}
class EmojiFilter implements Filter {
    @Override
    public boolean doFilter (Msg m) {
        String msg = m.getMsg ();
        msg = msg.replace ("(￣▽￣)","O(∩_∩)O");
        m.setMsg (msg);
        return true;
    }
}

class UrlFilter implements Filter {
    @Override
    public boolean doFilter (Msg m) {
        String msg = m.getMsg ();
        msg = msg.replace ("91porn.com","http://www.91porn.com");
        m.setMsg (msg);
        return true;
    }
}
class SensitiveFilter implements Filter{
    @Override
    public boolean doFilter (Msg m) {
        String msg = m.getMsg ();
        msg = msg.replaceAll ("996","955");
        m.setMsg (msg);
        return false;
    }
}

class FilterChain implements Filter{
    List<Filter>  filters = new ArrayList<>();

    public FilterChain add(Filter f){
        filters.add (f);
        return this;
    }

    @Override
    public boolean doFilter(Msg m){
        for (Filter filter : filters) {
            if (!filter.doFilter (m)) {
                return false;
            }
        }
        return true;
    }
}