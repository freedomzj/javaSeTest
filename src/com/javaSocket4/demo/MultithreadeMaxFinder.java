package com.javaSocket4.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadeMaxFinder {

	public static int max(int[] data) throws InterruptedException, ExecutionException{
		if(data.length==1){
			return data[0];
		}else if(data.length==0){
			throw new IllegalArgumentException();
		}
		//将任务分解为二部分
		FindMaxTask task1=new FindMaxTask(data, 0, data.length/2);
		FindMaxTask task2=new FindMaxTask(data, data.length/2, data.length);
		//创建2个线程
		ExecutorService service=Executors.newFixedThreadPool(2);
		Future<Integer> future1=service.submit(task1);
		Future<Integer> future2=service.submit(task2);
		
		return Math.max(future1.get(),future2.get()) ;
	}
	
	public static void main(String[] args) {
		try {
			int[] arry=new int[]{1,2,3,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
			System.out.println(max(arry));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
