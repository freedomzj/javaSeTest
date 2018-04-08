package com.stream.bean;

import static java.util.Comparator.comparing;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.lambda.bean.Apple;

public class ParallelTest {
	
	
	public static void main(String[] args) throws InterruptedException {
		LocalTime localTime=LocalTime.now();
		System.out.println("程序开始时间"+localTime);
		
		//装载1千万个对象6s  Integer 4s int 4s
		List<Apple> inventory = new ArrayList<>();
		for (Integer i = 0; i < 10000000; i++) {
			BiFunction<String, Integer, Apple> c3 = Apple::new;
			inventory.add(c3.apply("red", i));
		}
		
		LocalTime startTime=LocalTime.now();
		//list里面装有1千万个对象 假设取出所有宽度能对5整除的 
		
		
		//stream并行运行耗时  1.4s
//		List<Apple> heavyApples2 = inventory.parallelStream().filter((Apple a) -> a.getWidth() %5==0).collect(toList());
		
		//普通迭代方式  1.4S
//		List<Apple> apples=new ArrayList<>(inventory.size());
//		for (int i = 0; i < inventory.size(); i++) {
//			Apple a=inventory.get(i);
//			if(a.getWidth()%5==0)
//				apples.add(a);
//			
//		}
//		apples.sort(comparing(Apple::getWidth));
		
		//40S
//		apples.forEach(
//				item -> System.out.println("this apple width:" + item.getWidth() + " color is:" + item.getColor()));
		
		
		//一分多钟  
		inventory.parallelStream().forEach(
				item -> System.out.println(Thread.currentThread().getName()+"this apple width:" + item.getWidth() + " color is:" + item.getColor()));
		
		LocalTime endTime=LocalTime.now();
		System.out.println("迭代开始时间"+startTime);
		System.out.println("迭代结束时间:"+endTime);
		Thread.sleep(30000);
	}
	

}
