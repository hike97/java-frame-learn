package designpattern.factory.factory.method;


import designpattern.factory.factory.entity.IBMMouse;
import designpattern.factory.factory.entity.Mouse;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/6
 * @return
 */
public class IBMMouseFactory extends LenovoMouseFactory {
    @Override
    public Mouse createMouse() {
        return new IBMMouse ();
    }
}