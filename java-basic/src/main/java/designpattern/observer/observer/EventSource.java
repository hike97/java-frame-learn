package designpattern.observer.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class EventSource {
    List<EventListener> listeners = new ArrayList<EventListener>();
    public void register(EventListener eventListener){
        listeners.add(eventListener);
    }
    public void publishEvent(Event event){
        for(EventListener listener: listeners){
            listener.processEvent(event);
        }
    }
}