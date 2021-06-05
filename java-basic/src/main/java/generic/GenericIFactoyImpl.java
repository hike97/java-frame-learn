package generic;

/**
 * @author hike97
 * @create 2021-06-05 14:07
 * @desc 抽象接口的子类
 **/
//这里泛型形参 应该和泛型接口一致 为T N 顺序可变
public class GenericIFactoyImpl<T, N> implements GenericIFactory<T, N> {

    @Override
    public T nextObject () {
        return null;
    }

    @Override
    public N nextNumber () {
        return null;
    }
}
