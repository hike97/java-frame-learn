package juc.mycode;

import lombok.Getter;

import java.util.concurrent.*;

/**
 * @Author hike97
 * @Description 秦灭六国一统中原
 * @create 2020-09-24 10:00
 * @Modified By:
 **/
public class CountdownLantchDemo {


    public static void main (String[] args) {
        CountDownLatch latch = new CountDownLatch (6);
        //		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder ().setNameFormat("%d").build();
        //		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor (6, 6, 0L, TimeUnit.SECONDS,
        //				new LinkedBlockingQueue<> (1),Executors.defaultThreadFactory (),new ThreadPoolExecutor.AbortPolicy ());
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread (() -> {
                System.out.println (Thread.currentThread ().getName () + ": 公元前" + States.getStatesById (finalI).getYear () + "年," + States.getStatesById (finalI).getStateName () + "被灭");
                latch.countDown ();
            }, String.valueOf (i)).start ();
            //			poolExecutor.execute (()->{
            //				System.out.println ("公元前"+States.getStatesById (finalI).getYear ()+"年,"+States.getStatesById (finalI).getStateName ()+"被灭");
            //				latch.countDown ();
            //			});
            //			try {
            //				TimeUnit.SECONDS.sleep (2);
            //			} catch (InterruptedException e) {
            //				e.printStackTrace ();
            //			}
        }
        try {
            latch.await ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        System.out.println ("公元前221年，秦统一六国");
        //		poolExecutor.shutdown ();
    }
}

enum States {
    /**
     * 战国七雄
     */
    ZERO (0, "韩", 230),
    ONE (1, "赵", 228),
    TWO (2, "魏", 225),
    THREE (3, "楚", 223),
    FOUR (4, "燕", 222),
    FIVE (5, "齐", 221);

    @Getter
    private Integer id;
    @Getter
    private String stateName;
    @Getter
    private int year;

    States (Integer id, String stateName, int year) {
        this.id = id;
        this.stateName = stateName;
        this.year = year;
    }

    public static States getStatesById (int id) {
        for (States states : values ()) {
            if (id == states.getId ()) {
                return states;
            }
        }
        return null;
    }
}
