package designpattern.factory.factory.abstractf;


import designpattern.factory.factory.entity.KeyBoard;
import designpattern.factory.factory.entity.LenovoKeyBoard;
import designpattern.factory.factory.entity.LenovoMouse;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class LenovoComputerFactory implements ComputerFactory {

    @Override
    public Mouse createMouse() {
        return new LenovoMouse ();
    }

    @Override
    public KeyBoard createKeyBoard() {
        return new LenovoKeyBoard ();
    }
}