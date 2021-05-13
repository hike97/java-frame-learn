package designpattern.iterator.v2;

import designpattern.iterator.Collection_;
import designpattern.iterator.Iterator_;

/**
 * @author hike97 2month
 * @create 2021-05-12 21:13
 * @desc 链表实现ArrayList
 **/
public class Main {
    public static void main (String[] args) {
        LinkedList_ list_ = new LinkedList_ ();
        for (int i = 0; i < 15; i++) {
            list_.add (i);
        }
        System.out.println (list_.size ());
        Iterator_ iterator = list_.iterator ();
        while (iterator.hasNext ()){
            System.out.println (iterator.next ());
        }
    }
}

class LinkedList_ implements Collection_ {
    Node head = null;
    Node tail = null;
    private int size = 0;
    @Override
    public void add(Object o) {
        Node n = new Node (o);
        if (head==null){
            head=n;
            tail=n;
        }
        tail.next=n;
        tail=n;
        size++;
    }
    @Override
    public int size(){
        return size;
    }

    private class Node {
        private Object o ;
        Node next;

        public Node (Object o) {
            this.o = o;
            next = null;
        }
    }

    @Override
    public Iterator_ iterator () {
        return new ListItr();
    }

    @Override
    public String toString () {
        return "LinkedList_{" +
                "head=" + head +
                ", tail=" + tail +
                ", size=" + size +
                '}';
    }

    private class ListItr implements Iterator_{
        Object curVal;
        @Override
        public boolean hasNext () {
            return head!=null;
        }

        @Override
        public Object next () {
            curVal = head.o;
            head = head.next;
            return curVal;
        }
    }
}
