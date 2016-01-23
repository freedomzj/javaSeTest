package com.bean;

public class DemoTest {

	public static void main(String[] args) {
		
		Apple apple=new Apple("iphone");
		apple.doApple();
		
		Apple apple1=new Apple("iphone6");
		apple1.doApple();
		
//		Thread thread=new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				Apple apple1=new Apple();
//				apple1.doApple();
//			}
//		});
//		thread.run();
		
		
	}
}
