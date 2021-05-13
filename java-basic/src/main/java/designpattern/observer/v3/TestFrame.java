package designpattern.observer.v3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author hike97 2month
 * @create 2021-05-13 13:15
 * @desc JWT 实际用例
 **/
public class TestFrame extends Frame {
    public void launch() {
        Button b = new java.awt.Button ("press me");
        b.addActionListener(new MyActionListener());
        b.addActionListener(new MyActionListener02());
        this.add(b);
        this.pack();

        this.addWindowListener(new WindowAdapter (){

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
        this.setLocation(400, 400);
        this.setVisible(true);

    }

    public static void main (String[] args) {
        new TestFrame ().launch ();
    }
    //Observer 模式
    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed (ActionEvent e) {
            ((Button)e.getSource()).setLabel("press me again!");
            System.out.println("button pressed!");
        }
    }

    private class MyActionListener02 implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            System.out.println ("botton pressed 2!");
        }
    }
}
