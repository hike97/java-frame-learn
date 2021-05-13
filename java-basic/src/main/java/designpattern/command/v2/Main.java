package designpattern.command.v2;

import designpattern.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-13 16:25
 * @desc 责任链实现 一系列操作后回滚
 **/
public class Main {
    public static void main (String[] args) {
        CommandFilterChain chain = new CommandFilterChain ();
//        new InsertCommandFilter ()


    }
}

/**
 * command filter
 */
interface Filter{

    void doFilter(Command command,CommandFilterChain chain);

}

class InsertCommandFilter implements Filter {
    @Override
    public void doFilter (Command command, CommandFilterChain chain) {
        command.doit ();
        doFilter (command,chain);
        command.undo ();
    }
}

class CommandFilterChain {

    List<Filter> filters = new ArrayList<> ();
    int index = 0;

    public CommandFilterChain add(Filter f){
        filters.add (f);
        return this;
    }

    public void doFilter(Command commad, CommandFilterChain chain){
        if (index == filters.size () ) {
            return ;
        }
        Filter f = filters.get (index);
        index ++;
        f.doFilter (commad,chain);
    }
    
}
