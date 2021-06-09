package designpattern.factory.factory.abstractf;


import designpattern.factory.factory.entity.KeyBoard;
import designpattern.factory.factory.entity.Mouse;

public interface ComputerFactory {
    Mouse createMouse();
    KeyBoard createKeyBoard();
}
