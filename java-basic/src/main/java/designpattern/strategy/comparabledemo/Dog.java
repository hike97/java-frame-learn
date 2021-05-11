package designpattern.strategy.comparabledemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hike97 2month
 * @create 2021-05-07 18:28
 * @desc
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dog implements Comparable<Dog>{

	private int food;

	@Override
	public int compareTo (Dog dog) {
		if (this.food< dog.food) return -1;
		else if (this.food> dog.food) return 1;
		else return 0;
	}
}
