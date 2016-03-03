package com.test;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class SockExchange {

	public static void main(String[] args) {
		BlockingQueue<Integer> orderQueue = new LinkedBlockingQueue<Integer>();
		CountDownLatch startSignal=new CountDownLatch(1);
		CountDownLatch stopSignal=new CountDownLatch(200);
		Seller seller = new Seller(orderQueue,startSignal,stopSignal);
		Thread[] sellerThread = new Thread[100];
		for (int i = 0; i < 100; i++) {
			sellerThread[i] = new Thread(seller);
			sellerThread[i].start();
		}
		Buyer buyer = new Buyer(orderQueue,startSignal,stopSignal);
		Thread[] buyerThread = new Thread[100];
		for (int i = 0; i < 100; i++) {
			buyerThread[i] = new Thread(buyer);
			buyerThread[i].start();

		}
		try {
			while (System.in.read() != '\n') {

			}
		} catch (IOException ex) {

		}

		System.out.println("Terminating");
		for (Thread t : sellerThread) {
			t.interrupt();
		}
		for (Thread t : buyerThread) {
			t.interrupt();
		}
		try{
			startSignal.await();
		}catch(InterruptedException iex){
			
		}
		System.out.println("Closing down");

	}
}

class Seller implements Runnable {
	private BlockingQueue orderQueue;
	private boolean shutdownRequest = false;
	private static int id;
	private CountDownLatch startLatch,stopLatch;

	public Seller(BlockingQueue orderQueue,CountDownLatch startLatch,CountDownLatch stopLatch) {
		this.orderQueue = orderQueue;
		this.startLatch=startLatch;
		this.stopLatch=stopLatch;
	}

	@Override
	public void run() {
		try{
			startLatch.await();	
		}catch(InterruptedException ex){
			
		}
		while (shutdownRequest == false) {
			Integer quantity = (int) (Math.random() * 100);
			try {
				orderQueue.put(quantity);
				System.out.println("Sell order by " + Thread.currentThread().getName() + ":" + quantity);
			} catch (InterruptedException e) {
				shutdownRequest=true;
			}

		}
		
		stopLatch.countDown();

	}

}

class Buyer implements Runnable {

	private BlockingQueue orderQueue;
	private boolean shutdownRequest = false;
	private CountDownLatch startLatch,stopLatch;

	public Buyer(BlockingQueue orderQueue,CountDownLatch startLatch,CountDownLatch stopLatch) {
		this.orderQueue = orderQueue;
		this.startLatch=startLatch;
		this.stopLatch=stopLatch;
	}

	@Override
	public void run() {
		try{
			startLatch.await();	
		}catch(InterruptedException ex){
			
		}
		while (shutdownRequest == false) {
			try {
				Integer quantity = (Integer) orderQueue.take();
				System.out.println("Buy order by " + Thread.currentThread().getName() + ":" + quantity);

			} catch (InterruptedException iex) {
				shutdownRequest=true;
			}
		}
		
		stopLatch.countDown();
	}

}
