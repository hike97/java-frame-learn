package review;

/**
 * @author hike97
 * @create 2021-08-29 23:04
 * @desc wait and notify
 **/
public class WaitAndNotifyDemo {
    public static void main (String[] args) throws InterruptedException {
        MyThread myThread = new MyThread ();
        myThread.start ();
        // 主线程睡眠3s
        Thread.sleep (3000);
        synchronized (myThread) {
            try {
                System.out.println ("before wait");
                // 阻塞主线程
                myThread.wait ();
                System.out.println ("after wait");
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run () {
        synchronized (this) {
            System.out.println ("before notify");
            //说明: 由于先调用了notify，再调用的wait，此时主线程还是会一直阻塞。
            notify ();
            System.out.println ("after notify");
        }
    }
}