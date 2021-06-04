package juc.container.blockingqueue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author hike97 2month
 * @create 2021-05-18 16:41
 * @desc
 **/
public class T01_TransferQueue {

    public static void main (String[] args) {
        char[] aI = "1234567".toCharArray ();
        char[] aC = "ABCDEFG".toCharArray ();

        TransferQueue<Character> queue = new LinkedTransferQueue<Character> ();
        new Thread (() -> {
            try {
                for (char c : aC) {
                    queue.transfer (c);
                    System.out.print (queue.take ());
                }

            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }, "t2").start ();

        new Thread (() -> {
            try {
                for (char c : aI) {
                    System.out.print (queue.take ());
                    queue.transfer (c);
                }

            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }, "t1").start ();


    }

}
