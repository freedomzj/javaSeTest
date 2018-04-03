package com.lambda.bean;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ParallelTest {
	
	
	public static void main(String[] args) throws InterruptedException {
		LocalTime localTime=LocalTime.now();
		System.out.println("程序开始时间"+localTime);
		
		List<Apple> inventory = new ArrayList<>();
		for (int i = 0; i < 10000000; i++) {
			Apple a=new Apple("red", i);
			inventory.add(a);
		}
		
		LocalTime startTime=LocalTime.now();
		//list里面装有1千万个对象 假设取出所有宽度能对5整除的 
		
		
		//stream并行运行耗时 单核 1.4s
//		List<Apple> heavyApples2 = inventory.parallelStream().filter((Apple a) -> a.getWidth() %5==0).collect(toList());
		
		//普通迭代方式  单核 1.4S
		List<Apple> apples=new ArrayList<>(inventory.size());
		for (int i = 0; i < inventory.size(); i++) {
			Apple a=inventory.get(i);
			if(a.getWidth()%5==0)
				apples.add(a);
			
		}
		apples.sort(comparing(Apple::getWidth));
		
		//40S
//		apples.forEach(
//				item -> System.out.println("this apple width:" + item.getWidth() + " color is:" + item.getColor()));
		
		
		//一分多钟  
		apples.parallelStream().forEach(
				item -> System.out.println(Thread.currentThread().getName()+"this apple width:" + item.getWidth() + " color is:" + item.getColor()));
		LocalTime endTime=LocalTime.now();
		System.out.println("迭代开始时间"+startTime);
		System.out.println("迭代结束时间:"+endTime);
	}
	

}
