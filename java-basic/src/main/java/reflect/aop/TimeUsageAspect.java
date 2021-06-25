package reflect.aop;


/**
 * 日志切面
 *
 * @author hike97
 */
public class TimeUsageAspect implements Aspect {

    long start;

    @Override
    public void before () {
        start = System.currentTimeMillis ();
    }

    @Override
    public void after () {
        var usage = System.currentTimeMillis () - start;
        System.out.format ("time usage : %dms\n", usage);

    }
}
