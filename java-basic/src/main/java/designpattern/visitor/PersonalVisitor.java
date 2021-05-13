package designpattern.visitor;

/**
 * @author hike97 2month
 * @create 2021-05-13 12:41
 * @desc 个人访问者  根据角色进行折扣
 **/
public class PersonalVisitor implements Visitor{

    double totalPrice;

    @Override
    public void visitCpu (Cpu cpu) {
        totalPrice += cpu.getPrice ()*0.9;
    }

    @Override
    public void visitMemory (Memory memory) {
        totalPrice += memory.getPrice ()*0.9;
    }

    @Override
    public void visitBoard (Board board) {
        totalPrice += board.getPrice ()*0.9;
    }
}
