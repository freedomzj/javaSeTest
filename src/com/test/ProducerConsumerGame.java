package com.test;

public class ProducerConsumerGame {

	public static void main(String[] args) {
		Bucket bucket = new Bucket();
		new Thread(new ProducerConsumerGame().new Producer(bucket)).start();
		new Thread(new ProducerConsumerGame().new Consumer(bucket)).start();
	}

	final class Consumer implements Runnable {

		private Bucket bucket;

		public Consumer(Bucket bucket) {
			this.bucket = bucket;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				bucket.get();
			}

		}

	}
	final class Producer implements Runnable {

		private Bucket bucket;

		public Producer(Bucket bucket) {
			this.bucket = bucket;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				bucket.put((int) (Math.random() * 100));
			}
		}

	}
}
class Bucket {
	private int packOfBalls;
	private boolean avilable = false;

	public synchronized int get() {
		if (avilable == false) {
			try {
				wait();
			} catch (InterruptedException ex) {

			}
		}
		System.out.println("Consumer Got;" + packOfBalls);
		avilable = false;
		notify();
		return packOfBalls;
	}
	public synchronized void put(int packOfBalls) {
		if (avilable) {
			try {
				wait();
			} catch (InterruptedException ex) {

			}
		}
		this.packOfBalls = packOfBalls;

		avilable = true;
		System.out.println("Producer put;" + packOfBalls);
		notify();

	}
}