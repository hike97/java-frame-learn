package designpattern.singleton;

/**
 * @author hike97 2month
 * @create 2021-05-06 23:25
 * @desc 饿汉式單例模式
 * 类加载到内存后就实例化一个单例，jvm线程安全
 * 简单实用，推荐使用
 * 唯一缺点：不管用到与否，类加载时就完成实例化
 **/
public class Mgr01 {

	private static final Mgr01 INSTANCE /*= new Mgr01 ()*/;
	static {
		INSTANCE = new Mgr01 ();
	}
	private Mgr01 () {
	}
	public static Mgr01 getInstance() {
		return INSTANCE;
	}

	public void m() {
		System.out.println ("m");
	}

	public static void main (String[] args) {
		Mgr01 mgr01 = Mgr01.getInstance ();
		Mgr01 mgr02 = Mgr01.getInstance ();
		System.out.println (mgr01 == mgr02);
	}
}
