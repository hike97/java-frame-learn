package juc.sychronizedlearn;

/**
 * @ClassName T03_Sleep_Yeild_Join
 * @Description TODO
 * @Author hike97
 * @Date 2021/4/13 14:36
 * @Version 1.0
 **/
public class T03_Sleep_Yeild_Join {
    public static void main(String[] args) {
//        testYield();
        testJoin();
    }

    /**
     * yield 让出cpu 返回到就绪状态
     */
    static void testYield(){
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                if(i%10 == 0){
                    Thread.yield();
                }
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("B" + i);
                if(i%10 == 0){
                    Thread.yield();
                }
            }
        }).start();
    }

    /**
     * join 等待其它线程执行
     * interrupt 需要catch 异常 在异常代码块中执行
     * 不建议调用stop 方法
     */
    static void testJoin(){
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("t2 start ---->");
                t1.join();
                System.out.println("t2 end ---->");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
