package designpattern.singleton;

public enum Mgr05 {
    /**
     * 实例
     */
    INSTANCE;

    public static void main (String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread (() -> System.out.println (Mgr05.INSTANCE.hashCode ())).start ();
        }
    }
}

/**
 * 伪代码
 */
/*
public final class Mgr05 extends Enum<Mgr05> {
	private static final Mgr05 INSTANCE;
	private static final Mgr05[] $VALUES;
	static {
		INSTANCE = new Mgr05 ("INSTANCE",0);
		$VALUES[0] = INSTANCE;
	}

	private Mgr05 (String name,int ordinal) {
		super(name,ordinal);
	}

	public static Mgr05[] values(){
		return $VALUES.clone ();
	}

	public static Mgr05 valueOf(String name){
		return Enum.valueOf (designPattern.singleton.Mgr05.class,name);
	}
}*/
