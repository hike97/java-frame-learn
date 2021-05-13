package designpattern.builder;

/**
 * @author hike97 2month
 * @create 2021-05-13 0:14
 * @desc lombok builder测试
 **/


import lombok.Builder;
import lombok.Data;

/**
 * 类似于lombok 的builder注解
 * */
@Data
@Builder
public class PersonV2 {
    int id;
    String name;
    int age;
    double weight;
    int score;
    Location loc;

    public static void main (String[] args) {
        PersonV2 personV2
                = new PersonV2Builder ().id (1).name ("kk").
                loc (new Location.LocationBuilder ().roomNo ("1111").street ("金水桥大街").build ()).build ();
        System.out.println (personV2);
    }
}

