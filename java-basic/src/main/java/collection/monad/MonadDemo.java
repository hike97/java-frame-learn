package collection.monad;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author hike97
 * @create 2021-06-15 18:01
 * @desc
 **/
public class MonadDemo {
    //什么是monad? 简单说，Monad就是一种设计模式，表示将一个运算过程，通过函数拆解成互相连接的多个步骤。
    // 你只要提供下一步运算所需的函数，整个运算就会自动进行下去。
    //https://www.ruanyifeng.com/blog/2015/07/monad.html

    /**
     * 为什么用optional? 因为optional会保证数据安全
     */
    @Test
    public void optionalTest () {
        Optional<Integer> x = Optional.empty ();
        var y = x.map (a -> a * a);
        System.out.println (y);
    }
}
