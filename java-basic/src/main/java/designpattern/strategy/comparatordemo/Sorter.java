package designpattern.strategy.comparatordemo;

import designpattern.strategy.comparabledemo.Dog;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.swap;

/**
 * @author hike97 2month
 * @create 2021-05-07 18:19
 * @desc 支持comparator的比较器
 * 策略模式是指做同一件事情的不同的执行方式
 **/
public class Sorter<T> {

	public void sort(T[] arr,Comparator<T> comparator){
		for (int i = 0; i < arr.length; i++) {
			int minPos = i;
			for (int j = i+1; j <arr.length ; j++) {
				minPos =  comparator.compare (arr[j],arr[minPos])==-1?j:minPos;
			}
			swap(arr,i,minPos);
		}
	}

	public static void main (String[] args) {
		List<Dog> dogs = Arrays.asList (new Dog (3), new Dog (2), new Dog (1));
		Dog[] dogsArr = dogs.toArray (new Dog[dogs.size ()]);
		Sorter<Dog> sorter = new Sorter<> ();
		sorter.sort (dogsArr, (o1, o2) -> {
			if (o1.getFood ()<o2.getFood ())return -1;
			if (o1.getFood ()>o2.getFood ())return 1;
			return 0;
		});
		System.out.println (Arrays.toString (dogsArr));
	}
}
