package com.lambda.bean;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class Test {

	public static void main(String[] args) {

		List<Apple> inventory = new ArrayList<>();
		inventory.add(new Apple("red", 100));
		inventory.add(new Apple("red", 200));
		inventory.add(new Apple("green", 160));
		inventory.add(new Apple("black", 300));
		
		
		List<String> str = Arrays.asList("c","a","b","C","A","B");
//		str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
		 str.sort(String::compareToIgnoreCase);
		str.forEach(item ->System.out.println(item));
		
		Function<String, Integer> stringToInteger = Integer::parseInt;
		stringToInteger.apply("100");
		
		List<String> listOfStrings = new ArrayList<>();
		listOfStrings.add("a");
		listOfStrings.add("b");
		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
		
		System.out.println(nonEmpty.size());
		
		
	    Callable<Integer> c = () -> 42;
	    
		new Thread(() -> System.out.println("Hello world")).run();

		inventory.sort((Apple a1, Apple a2) -> a1.getWidth().compareTo(a2.getWidth()));
		inventory.forEach(item -> System.out.println(item.getColor() + " width is" + item.getWidth()));

		inventory.sort(comparing(Apple::getWidth));
		
		List<Apple> greenApple = filterApples(inventory, Apple::isGreenApple);

		List<Apple> heavyApple = filterApples(inventory, Apple::isHeavyApple);

		List<Apple> testApple = filterApples(inventory, (Apple a) -> a.getWidth() < 80 || "red".equals(a.getColor()));

		List<Apple> redApples = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));

		List<Apple> heavyApples2 = inventory.parallelStream().filter((Apple a) -> a.getWidth() > 150).collect(toList());

		redApples.forEach(
				item -> System.out.println("this apple width:" + item.getWidth() + " color is:" + item.getColor()));

		heavyApple.forEach(item -> {
			System.out.println(item.getWidth());
			System.out.println(item.getColor());
		});
		// greenApple.forEach(item -> System.out.println(item.getColor()));

		File[] hiddenFiles = new File(".").listFiles(File::isHidden);

		try {
			String result = processFile((BufferedReader br) -> br.readLine());
			String twoLines =
					processFile((BufferedReader br) -> br.readLine() + br.readLine());
			System.out.println(result+""+twoLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println( hiddenFiles);
		// for (int i = 0; i < hiddenFiles.length; i++) {
		// System.out.println(hiddenFiles[i].getName());
		// }

		process(() -> System.out.println("This is awesome!!"));

	}

	public static void process(Runnable r) {
		r.run();
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}

	
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(".gitignore"))) {
			return p.process(br);
		}
	}

	static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public Integer random() {
		Integer a = 0;
		Random random = new Random();
		a = random.nextInt(100);
		return a;
	}

}
