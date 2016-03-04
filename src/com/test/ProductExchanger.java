package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ProductExchanger {
	

	public static Exchanger<List<Integer>> exchanger = new Exchanger<List<Integer>>();

	public static void main(String[] args) {
		Thread producer=new Thread(new Producer());
		Thread consumer=new Thread(new Consumer());
		producer.start();
		consumer.start();
	try{
		while(System.in.read()!='\n'){
			
		}
	}catch(Exception ex){
		ex.printStackTrace();
		
	}
	producer.interrupt();
	consumer.interrupt();
	}
	
	static class Producer implements Runnable{
		private static List<Integer> buffer=new ArrayList<Integer>();
		private boolean okToRun=true;
		private final int BUFFSIZE=10;
		@Override
		public void run() {
			int j=0;
			while(okToRun){
				if(buffer.isEmpty()){
					try{
						for (int i = 0; i < BUFFSIZE; i++) {
							buffer.add((int)(Math.random() *100));
						}
						Thread.sleep((int)(Math.random()*1000));
						System.out.println("Producer Buffer:");
						for(int i:buffer){
							System.out.println(i+",");
						}
						System.out.println();
						System.out.println("Exchanging");
						buffer=ProductExchanger.exchanger.exchange(buffer);
					}catch(InterruptedException ex){
						okToRun=false;
					}
				}
			}
		}
	}
	static class Consumer implements Runnable{
		private static List<Integer> buffer=new ArrayList<Integer>();
		private boolean okToRun=true;
		@Override
		public void run() {
			while(okToRun){
				try{
					if(buffer.isEmpty()){
						buffer=ProductExchanger.exchanger.exchange(buffer);
						System.out.println("Consumer Buffer:");
						for(int i: buffer){
							System.out.println(i+",");
						}
						System.out.println("\n");
						Thread.sleep((int)(Math.random() *1000));
						buffer.clear();
					}
				}catch(InterruptedException ex){
					okToRun=false;
				}
			}
		}
		
	}
}


