package designpattern.observer.v3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hike97 2month
 * @create 2021-05-13 13:59
 * @desc 用户自定义模拟frameobserver实现
 **/
public class TestFrameCustom {
    public static void main (String[] args) {
        Button_ button_ = new Button_ ();
        button_.addActionListener_ (new MyActionListener_ ());
        button_.addActionListener_ (new MyActionListener_2 ());
        button_.buttonPressed ();
    }
}
class Button_ {
    private List<ActionListener_> actionListeners =  new ArrayList<> ();
    public void addActionListener_(ActionListener_ listener){
        actionListeners.add (listener);
    }
    public void buttonPressed(){
        ActionEvent event = new ActionEvent (System.currentTimeMillis (), this);
        for (int i = 0; i < actionListeners.size (); i++) {
            ActionListener_ listener_ = actionListeners.get (i);
            listener_.actionPerformed (event);
        }
    }
}
interface ActionListener_ {
    void actionPerformed(ActionEvent e);
}
class MyActionListener_ implements ActionListener_{

    @Override
    public void actionPerformed (ActionEvent e) {
        System.out.println ("button pressed");
    }
}

class MyActionListener_2 implements ActionListener_ {
    @Override
    public void actionPerformed (ActionEvent e) {
        System.out.println ("button pressed 2");
    }
}

class ActionEvent {
    long when;
    Object source;

    public ActionEvent (long when, Object source) {
        this.when = when;
        this.source = source;
    }

    public long getWhen () {
        return when;
    }

    public Object getSource () {
        return source;
    }
}
