package designpattern.factory.factory.method;


import designpattern.factory.factory.entity.HpMouse;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class HpMouseFactory implements MouseFactory {

    @Override
    public Mouse createMouse() {
        return new HpMouse ();
    }
}