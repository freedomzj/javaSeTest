package com.test;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class LuckyNumberGenerator {

	public static void main(String[] args) {
		TransferQueue<String> queue = new LinkedTransferQueue();
		Thread producer = new Thread(new Producer(queue));
		producer.setDaemon(true);
		producer.start();

		for (int i = 0; i < 10; i++) {
			Thread consumer = new Thread(new Consumer(queue));
			consumer.setDaemon(true);
			consumer.start();
			try {
				Thread.sleep(2000);

			} catch (InterruptedException ex) {

			}

		}
	}

}

class Producer implements Runnable {
	private final TransferQueue<String> queue;

	Producer(TransferQueue<String> queue) {
		this.queue = queue;
	}

	private String produce(){
		return "your lucky number "+(new Random().nextInt(100));
	}
	@Override
	public void run() {
		try{
			while(true){
				if(queue.hasWaitingConsumer()){
					queue.transfer(produce());
				}
				TimeUnit.SECONDS.sleep(1);
			}
		}
		catch(InterruptedException ex){
			
		}

	}

}


class Consumer implements Runnable{
	private final TransferQueue<String> queue;
	Consumer(TransferQueue<String> queue){
		this.queue=queue;
		
	}
	
	@Override
	public void run() {
		
		try{
			System.out.println("Consumer"+Thread.currentThread().getName()+queue.take());
		}catch(InterruptedException iex){
			
		}
		
		
	}
}
