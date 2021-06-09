package designpattern.factory.factory.abstractf;


import designpattern.factory.factory.entity.HpKeyBoard;
import designpattern.factory.factory.entity.HpMouse;
import designpattern.factory.factory.entity.KeyBoard;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class HpComputerFactory implements ComputerFactory {

    @Override
    public Mouse createMouse() {
        return new HpMouse ();
    }

    @Override
    public KeyBoard createKeyBoard() {
        return new HpKeyBoard ();
    }
}