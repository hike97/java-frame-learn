package designpattern.strategy.comparatordemo;

/**
 * @author hike97
 * @create 2021-05-07 19:01
 * @desc 自定义comparator
 **/
public interface Comparator<T> {
	int compare(T o1,T o2);

	default void m() {
		System.out.println ("m");
	}
}
