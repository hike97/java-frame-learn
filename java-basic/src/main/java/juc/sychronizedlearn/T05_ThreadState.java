package juc.sychronizedlearn;

/**
 * @ClassName T05_ThreadState
 * @Description TODO 线程状态
 * @Author hike97
 * @Date 2021/4/13 16:16
 * @Version 1.0
 **/
public class T05_ThreadState {
    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }
    public static void main(String[] args) {
        MyThread t = new MyThread();
        System.out.println(t.getState());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.getState());
    }
}
