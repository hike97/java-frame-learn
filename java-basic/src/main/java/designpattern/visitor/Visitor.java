package designpattern.visitor;

/**
 * @author hike97
 * @create 2021-05-13 11:57
 * @desc
 **/
public interface Visitor {
    void visitCpu(Cpu cpu);
    void visitMemory(Memory memory);
    void visitBoard(Board board);
}
