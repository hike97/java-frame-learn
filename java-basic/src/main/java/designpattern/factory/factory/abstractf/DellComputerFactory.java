package designpattern.factory.factory.abstractf;


import designpattern.factory.factory.entity.DellKeyBoard;
import designpattern.factory.factory.entity.DellMouse;
import designpattern.factory.factory.entity.KeyBoard;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class DellComputerFactory implements ComputerFactory {

    @Override
    public Mouse createMouse() {
        return new DellMouse ();
    }

    @Override
    public KeyBoard createKeyBoard() {
        return new DellKeyBoard ();
    }
}