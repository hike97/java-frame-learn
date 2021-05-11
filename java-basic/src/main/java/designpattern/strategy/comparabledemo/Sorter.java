package designpattern.strategy.comparabledemo;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.swap;

/**
 * @author hike97 2month
 * @create 2021-05-07 18:19
 * @desc 比较器
 **/
public class Sorter {

	public static void sort(Comparable[] arr){
		for (int i = 0; i < arr.length; i++) {
			int minPos = i;
			for (int j = i+1; j <arr.length ; j++) {
				minPos =  arr[j].compareTo (arr[minPos])==-1?j:minPos;
			}
			swap(arr,i,minPos);
		}
	}

	public static void main (String[] args) {
		List<Dog> dogs = Arrays.asList (new Dog (3), new Dog (2), new Dog (1));
		Dog[] dogsArr = dogs.toArray (new Dog[dogs.size ()]);
		Sorter.sort (dogsArr);
		System.out.println (Arrays.toString (dogsArr));
	}
}
