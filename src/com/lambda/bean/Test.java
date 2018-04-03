package com.lambda.bean;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;

public class Test {

	static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
	static {
		map.put("apple", Apple::new);
		map.put("orange", Orange::new);
	}

	//不将构造函数实例化却能够引用它,这个功能有一些有趣的应用。例如,你可以使用Map来 将构造函数映射到字符串值。
	///你可以创建一个giveMeFruit方法,给它一个String和一个 Integer,它就可以创建出不同重量的各种水果:
	public static Fruit giveMeFruit(String fruit, Integer weight) {
		return map.get(fruit.toLowerCase()).apply(weight);
	}

	public static void main(String[] args) {
		
		Apple applet= (Apple) giveMeFruit("aaa", 100);

		List<Apple> inventory = Arrays.asList(new Apple("red", 100), new Apple("red", 200), new Apple("green", 160),
				new Apple("black", 300));

		List<String> str = Arrays.asList("c", "a", "b", "C", "A", "B");
		// str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
		str.sort(String::compareToIgnoreCase);
		str.forEach(item -> System.out.println(item));

		Function<String, Integer> stringToInteger = Integer::parseInt;
		stringToInteger.apply("100");

		List<String> listOfStrings = Arrays.asList("a", "b");

		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
		Supplier<Apple> c1 = Apple::new;// 无参数构造 利用默认构造函数创建 Apple的Lambda表达式
		Apple a1 = c1.get(); // 调用Supplier的get方法 将产生一个新的Apple

		Function<Integer, Apple> c2 = Apple::new;
		Apple a2 = c2.apply(110);// 调用该Function函数的apply方法,并 给出要求的重量,将产生一个Apple

		BiFunction<String, Integer, Apple> c3 = Apple::new;// 2个参数构造
		Apple param3 = c3.apply("green", 110);// 调用该BiFunction函数的apply
												// 方法,并给出要求的颜色和重量,
												// 将产生一个新的Apple对象

		System.out.println(nonEmpty.size());

		Callable<Integer> c = () -> 42;

		new Thread(() -> System.out.println("Hello world")).run();

		inventory.sort((Apple b1, Apple b2) -> b1.getWidth().compareTo(b2.getWidth()));
		inventory.forEach(item -> System.out.println(item.getColor() + " width is" + item.getWidth()));

		inventory.sort(comparing(Apple::getWidth));

		List<Apple> greenApple = filterApples(inventory, Apple::isGreenApple);

		List<Apple> heavyApple = filterApples(inventory, Apple::isHeavyApple);

		List<Apple> testApple = filterApples(inventory, (Apple a) -> a.getWidth() < 80 || "red".equals(a.getColor()));

		List<Apple> redApples = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);

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
			String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
			System.out.println(result + "" + twoLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println( hiddenFiles);
		// for (int i = 0; i < hiddenFiles.length; i++) {
		// System.out.println(hiddenFiles[i].getName());
		// }

		process(() -> System.out.println("This is awesome!!"));

		// Lambda是Consumer中 accept方法的实现
		forEach(Arrays.asList(1, 2, 3, 4, 5, 6), (Integer i) -> System.out.print(i));

		// [7, 2, 6] lambda是function接口的apply 方法的实现
		List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());

		System.out.println(l.size());
		forEach(l, (Integer i) -> System.out.print(i.intValue()));

		IntPredicate testNumbers = (int i) -> i % 2 == 0;
		System.out.println(testNumbers.test(1000));

		Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
		System.out.println(oddNumbers.test(1000));
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

	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T i : list) {
			c.accept(i);
		}
	}

	public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for (T s : list) {
			result.add(f.apply(s));
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
