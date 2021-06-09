package designpattern.template.template;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class RoomForAmericanSinger extends KTVRoom {

    @Override
    protected void orderSong() {
        System.out.println("唱首英文歌...");
    }
}