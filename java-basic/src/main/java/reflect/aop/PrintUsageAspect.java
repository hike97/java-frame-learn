package reflect.aop;


/**
 * @author hike97
 * @create 2021-06-16 18:22
 * @desc
 **/
public class PrintUsageAspect implements Aspect {
    @Override
    public void before () {
        System.out.println ("开启日志");
    }

    @Override
    public void after () {
        System.out.println ("关闭日志");
    }
}
