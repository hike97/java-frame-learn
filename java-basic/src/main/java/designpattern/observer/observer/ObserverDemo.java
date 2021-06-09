package designpattern.observer.observer;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class ObserverDemo {
    public static void main(String[] args) {
        EventSource eventSource = new EventSource();
        SingleClickEventListener singleEvent = new SingleClickEventListener();
        eventSource.register(singleEvent);
        DoubleClickEventListener doubleEvent = new DoubleClickEventListener();
        eventSource.register(doubleEvent);
        Event event = new Event();
        event.setType("doubleclick");
        eventSource.publishEvent(event);

    }
}