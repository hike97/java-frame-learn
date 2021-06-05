package generic;

import lombok.Data;

import java.util.Random;

/**
 * @author hike97
 * @create 2021-06-05 13:59
 * @desc
 **/
@Data
public class RobotFactory implements GenericIFactory<String, Integer> {

    private String[] stringRobot = new String[]{"hello", "hi"};
    private Integer[] integerRobot = new Integer[]{111, 000};

    @Override
    public String nextObject () {
        Random random = new Random ();
        //[0,2) 生成一个 0 到 2 的随机数
        return stringRobot[random.nextInt (2)];
    }

    @Override
    public Integer nextNumber () {
        Random random = new Random ();
        return integerRobot[random.nextInt (2)];
    }

    public static void main (String[] args) {
        GenericIFactory<String, Integer> factory = new RobotFactory ();
        System.out.println (factory.nextObject ());
        System.out.println (factory.nextNumber ());
    }
}
