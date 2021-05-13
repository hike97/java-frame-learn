package designpattern.visitor;

/**
 * @author hike97 2month
 * @create 2021-05-13 11:59
 * @desc
 **/
public class Computer {
    ComputerPart cpu = new Cpu();
    ComputerPart memory = new Memory();
    ComputerPart board = new Board();

    public void accept(Visitor v){
        cpu.accept (v);
        memory.accept (v);
        board.accept (v);
    }

    public static void main (String[] args) {
        PersonalVisitor personalVisitor = new PersonalVisitor ();
        Computer computer = new Computer ();
        computer.accept (personalVisitor);
        System.out.println (personalVisitor.totalPrice);
    }
}

abstract class ComputerPart {

    abstract void accept (Visitor v);

    abstract double getPrice ();
}

class Cpu extends ComputerPart {
    @Override
    void accept (Visitor v) {
        v.visitCpu (this);
    }

    @Override
    double getPrice () {
        return 500;
    }
}
class Memory extends ComputerPart {
    @Override
    void accept (Visitor v) {
        v.visitMemory (this);
    }

    @Override
    double getPrice () {
        return 300;
    }
}
class Board extends ComputerPart {
    @Override
    void accept (Visitor v) {
        v.visitBoard (this);
    }

    @Override
    double getPrice () {
        return 200;
    }
}
