package designpattern.template.template;

/**
 * 〈〉
 *
 * @author Noah2021
 * @create 2021/3/9
 * @return
 */
public class TemplateDemo {
    public static void main(String[] args) {
        RoomForAmericanSinger americanSinger = new RoomForAmericanSinger();
        americanSinger.procedure();
        System.out.println("===================");
        RoomForChineseSinger chineseSinger = new RoomForChineseSinger();
        chineseSinger.procedure();
    }
}