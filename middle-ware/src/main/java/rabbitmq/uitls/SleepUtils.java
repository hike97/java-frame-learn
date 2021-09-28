package rabbitmq.uitls;

/**
 * @author hike97
 * @create 2021-09-27 13:00
 * @desc
 **/
public class SleepUtils {
    public static void sleep (int second) {
        try {
            Thread.sleep (1000 * second);
        } catch (InterruptedException _ignored) {
            Thread.currentThread ().interrupt ();
        }
    }
}

