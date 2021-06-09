package designpattern.observer.observer;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class SingleClickEventListener implements EventListener {

    @Override
    public void processEvent(Event event) {
        if("singleclick".equals(event.getType()))
            System.out.println("单击事件生效...");
    }
}