package designpattern.command;

/**
 * @author hike97 2month
 * @create 2021-05-13 15:11
 * @desc
 **/
public abstract class Command {
    /**
     * 执行操作
     */
     public abstract void doit();

    /**
     * 回退操作
     */
    public abstract void undo();
}
