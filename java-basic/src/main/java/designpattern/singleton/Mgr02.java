package designpattern.singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author hike97 2month
 * @create 2021-05-06 23:34
 * @desc 懒汉式模式
 **/
public class Mgr02 {

	private static Mgr02 INSTANCE;
	private Mgr02 () {
	}

	/**
	 * 不加锁会导致线程不安全问题
	 * @return
	 */
	public static synchronized Mgr02 getInsrtance() {
		if (INSTANCE == null) {
			try {
				TimeUnit.SECONDS.sleep (1);
			} catch (InterruptedException e) {
				e.printStackTrace ();
			}
			INSTANCE = new Mgr02 ();
		}
		return INSTANCE;
	}

	public static void main (String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread (()-> System.out.println (Mgr02.getInsrtance ().hashCode ())).start ();
		}
	}
}
