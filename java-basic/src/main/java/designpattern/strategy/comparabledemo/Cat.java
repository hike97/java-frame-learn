package designpattern.strategy.comparabledemo;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * @author hike97 2month
 * @create 2021-05-07 18:13
 * @desc
 **/
@Data
public class Cat implements Comparable{

    private int weight;

	@SneakyThrows
	@Override
	public int compareTo (Object o) {
		if (o instanceof Cat){
			Cat c = (Cat)o;
			if (this.getWeight ()<c.getWeight ()){
				return -1;
			}else if (this.getWeight ()>c.getWeight ()){
				return 1;
			}else {
				return 0;
			}
		}else {
			throw new Exception ("can not compare such class Exception");
		}
	}
}
