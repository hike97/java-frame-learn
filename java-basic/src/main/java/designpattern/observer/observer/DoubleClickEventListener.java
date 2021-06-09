package designpattern.observer.observer;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class DoubleClickEventListener implements EventListener {

    @Override
    public void processEvent(Event event) {
        if("doubleclick".equals(event.getType()))
            System.out.println("双击事件生效...");
    }
}