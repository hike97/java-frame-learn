package designpattern.template.template;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class RoomForChineseSinger extends KTVRoom {

    @Override
    protected void orderExtra() {
        System.out.println("来首英文版的恭喜发财...");
    }

    @Override
    protected void orderSong() {
        System.out.println("唱首中文歌...");
    }
}