package designpattern.singleton.singleton;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/7
 * @return
 */
public class StarvingSingleton {
    private StarvingSingleton(){}
    private static final StarvingSingleton instance = new StarvingSingleton();
    public static StarvingSingleton getInstance(){
        return instance;
    }
}