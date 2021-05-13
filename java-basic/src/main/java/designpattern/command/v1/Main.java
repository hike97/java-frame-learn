package designpattern.command.v1;

import designpattern.command.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-13 15:16
 * @desc 命令设计模式 有操作就得有操作回滚 例如 事务
 **/
public class Main {
    public static void main (String[] args) {
        Content c = new Content();

        Command insertCommand = new InsertCommand (c);
        Command copyCommand = new CopyCommand (c);
        Command deleteCommand = new DeleteCommand (c);
        List<Command> commandList = new ArrayList<> ();
        commandList.add (insertCommand);
        commandList.add (copyCommand);
        commandList.add (deleteCommand);
        for (int i = 0; i < commandList.size (); i++) {
//            Command o = (Command) Proxy.newProxyInstance (c.getClass ().getClassLoader (), new Class[]{Command.class}, new logHandler (commandList.get (i)));
            Command o = (Command) new CommandProxy (commandList.get (i),c).createProxy ();
            o.doit ();
            o.undo ();
        }

        System.out.println(c.msg);
    }
}
class CommandProxy implements MethodInterceptor {
    private Content content;
    private Object target;

    public CommandProxy (Object target,Content content) {
        this.target = target;
        this.content = content;
    }

    //增强器
    public Object createProxy () {
        //增强器
        Enhancer enhancer = new Enhancer ();
        enhancer.setSuperclass (Command.class);
        //将封装好的对象 set到intercept方法中
        enhancer.setCallback (this);
        return enhancer.create ();
    }

    @Override
    public Object intercept (Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        Object result = null;
        if ("doit".equals (method.getName ())){
            System.out.println (target.getClass ().getName ()+" 执行doit操做前msg的值: "+content.msg);
            result = method.invoke (target, objects);
            System.out.println (target.getClass ().getName ()+" 执行doit操做后msg的值: "+content.msg);
        }
        if ("undo".equals (method.getName ())){
            System.out.println (target.getClass ().getName ()+" 执行undo操做前msg的值: "+content.msg);
            result = method.invoke (target,objects);
            System.out.println (target.getClass ().getName ()+" 执行undo操做后msg的值: "+content.msg);
        }
//        Arrays.stream (target.getClass ().getDeclaredFields ()).forEach (System.out::println);
        return result;
    }
}

/**
 * 因为command 是类 不是接口 无法用jdk动态代理
 */
class logHandler implements InvocationHandler {

    private Object target;

    public logHandler (Object target) {
        this.target = target;
    }

    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if ("doit".equals (method.getName ())){
            System.out.println ("执行doit操做后msg的值"+proxy.getClass ().getField ("c").getName ());
            result = method.invoke (target, args);
        }
        if ("redo".equals (method.getName ())){
            System.out.println ("执行redo操做后msg的值"+proxy.getClass ().getField ("c").getName ());
            result = method.invoke (target,args);
        }
        return result;
    }
}

