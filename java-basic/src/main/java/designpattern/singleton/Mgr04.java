package designpattern.singleton;

/**
 * @author hike97 2month
 * @create 2021-05-06 23:34
 * @desc 静态内部类方式
 * JVM保证单例
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 **/
public class Mgr04 {

	private Mgr04 () {
	}

	/**
	 * 静态内部类在外层类加载时 不会被加载  只有在被调用时才会加载
	 */
	private static class Mgr04Holder {
		static {
			System.out.println ("Holder init");
		}

		private final static Mgr04 INSTANCE = new Mgr04();
	}
	public static Mgr04 getInsrtance() {
		//System.out.println ("Holder static method init");
		return Mgr04Holder.INSTANCE;
	}

	public static void main (String[] args) {
		//加载一个类时，其内部类不会同时被加载。
		// 一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生。
		//TestCode
		Mgr04 mgr04 = new Mgr04 ();
		System.out.println ("线程调用内部类方法===》");
		for (int i = 0; i < 100; i++) {
			new Thread (()-> System.out.println (Mgr04.getInsrtance ().hashCode ())).start ();
		}
	}
}
