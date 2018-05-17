package com.lambda.bean;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

	private String name;
	private static final Random random = new Random();

	public Shop(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice(String product) {
		return calculatePrice(product);
	}

	public String getFormatPrice(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}
	
	public String getRandomFormatPrice(String product) {
		double price = randomeCalculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}

	public static void randomDelay() {
		int delay = 500 + random.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public Future<Double> getPriceAsync(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);
			} catch (Exception ex) {
				futurePrice.completeExceptionally(ex);
			}
		}).start();
		return futurePrice;
	}

	private double randomeCalculatePrice(String product) {
		randomDelay();
		return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	private double calculatePrice(String product) {
		delay();
		return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
	}
}
