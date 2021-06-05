package generic;

/**
 * @author hike97
 * @create 2021-06-05 13:58
 * @desc 泛型接口
 **/
public interface GenericIFactory<T, N> {
    /**
     * 返回一个T
     *
     * @return
     */
    T nextObject ();

    /**
     * fan回一个N
     *
     * @return
     */
    N nextNumber ();
}
