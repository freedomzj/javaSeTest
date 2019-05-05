package com.lambda.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UseMap {
	public static void main(String[] args) {
		Map<String, Integer> pageVisits = new HashMap<>();

		String page = "https://agiledeveloper.com";

		incrementPageVisit(pageVisits, page);
		incrementPageVisit(pageVisits, page);

		System.out.println(pageVisits.get(page));

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 5; i++) {
			int temp = i;

			executorService.submit(new Runnable() {
				public void run() {
					// If uncommented the next line will result in an error
					// System.out.println("Running task " + i);
					// local variables referenced from an inner class must be
					// final or effectively final

					System.out.println("Running task " + temp + " " + System.currentTimeMillis());
				}
			});
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("lambda test");
		IntStream.range(0, 5).forEach(i -> executorService
				.submit(() -> System.out.println("Running task " + i + " " + System.currentTimeMillis())));

		IntStream.range(0, 5).limit(10).forEach(a -> System.out.println("a"));

		IntStream.range(0, 5).filter(UseMap::evenNumber).forEach(System.out::println);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("lambda test2");
		IntStream.range(0, 5).forEach(i -> executorService.submit(new Runnable() {
			public void run() {
				System.out.println("Running task " + i + System.currentTimeMillis());
			}
		}));

		executorService.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executorService.shutdown();

		List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9);
		int result = values.stream().filter(e -> e > 3).filter(e -> e % 2 == 0).map(e -> e * 2).findFirst().orElse(0);
		System.out.println("result:" + result);

		System.out.println(values.stream().mapToInt(UseMap::sumOfFactors).sum());

		new Thread(() -> System.out.println("In another thread")).start();

		totalSelectedValues(values, e -> e % 2 == 0);

		Function<Integer, Predicate<Integer>> isGreaterThan = pivot -> candidate -> candidate > pivot;
	}

	Function<Integer, Predicate<Integer>> isGreaterThan = (Integer pivot) -> {
		Predicate<Integer> isGreaterThanPivot = (Integer candidate) -> {
			return candidate > pivot;
		};

		return isGreaterThanPivot;
	};

	public static int sumOfFactors(int number) {
		return IntStream.rangeClosed(1, number).filter(i -> number % i == 0).sum();
	}

	public static boolean evenNumber(int a) {
		if (a % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int totalSelectedValues(List<Integer> values, Predicate<Integer> selector) {

		return values.stream().filter(selector).reduce(0, Integer::sum);
	}

	/*
	 * public static void incrementPageVisit(Map<String, Integer> pageVisits,
	 * String page) { if (!pageVisits.containsKey(page)) { pageVisits.put(page,
	 * 0); } pageVisits.put(page, pageVisits.get(page) + 1); }
	 */
	public static void incrementPageVisit(Map<String, Integer> pageVisits, String page) {
		pageVisits.merge(page, 1, (oldValue, value) -> oldValue + value);
	}
}