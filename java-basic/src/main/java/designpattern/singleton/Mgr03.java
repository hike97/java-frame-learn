package designpattern.singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author hike97 2month
 * @create 2021-05-06 23:34
 * @desc 懒汉式模式 减小锁的粒度 进行双重null判断
 **/
public class Mgr03 {
    //防止指令重排
	private static volatile Mgr03 INSTANCE;
	private Mgr03 () {
	}

	/**
	 * 不加锁会导致线程不安全问题
	 * @return
	 */
	public static  Mgr03 getInsrtance() {
		//外层判断 避免重复加锁
		if (INSTANCE == null) {
			try {
				TimeUnit.SECONDS.sleep (1);
			} catch (InterruptedException e) {
				e.printStackTrace ();
			}
			synchronized (Mgr03.class){
				if (INSTANCE==null){
					INSTANCE = new Mgr03 ();
				}
			}

		}
		return INSTANCE;
	}

	public static void main (String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread (()-> System.out.println (Mgr03.getInsrtance ().hashCode ())).start ();
		}
	}
}
