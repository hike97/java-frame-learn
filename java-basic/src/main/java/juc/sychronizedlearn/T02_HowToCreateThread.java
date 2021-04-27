package juc.sychronizedlearn;

/**
 * @ClassName T02_HowToCreateThread
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/12 16:23
 * @Version 1.0
 **/
public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Hello MyThread");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Hello MyRun");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println("Hello Lambda!");
        } ).start();
    }
}
