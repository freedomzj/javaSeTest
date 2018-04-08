package com.stream.bean;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 * @author 陈 争云, 占 宇剑, 和 司 磊
 *
 */
@SuppressWarnings("all")
public class StreamTest {

	public static void main(String[] args) throws Exception {
		// 清单 4. 构造流的几种常见方法
		// 1. Individual values
		Stream<String> stream = Stream.of("a", "b", "c");
		// 2. Arrays
		String[] strArray = new String[] { "a", "b", "c" };
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);
		// 3. Collections
		List<String> list = Arrays.asList(strArray);
		stream = list.stream();
		System.out.println(stream.toString());

		// 1. Array
		// String[] strArray1 = stream.toArray(String[]::new);
		// 2. Collection
		// List<String> list1 = stream.collect(Collectors.toList());
		// List<String> list2 =
		// stream.collect(Collectors.toCollection(ArrayList::new));
		// Set set1 = stream.collect(Collectors.toSet());
		// Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
		// 3. String
		// String str = stream.collect(Collectors.joining()).toString();
		// 一个 Stream 只可以使用一次，上面的代码为了简洁而重复使用了数次。
		// 常见的操作可以归类如下 Intermediate： map (mapToInt, flatMap 等)、 filter、
		// distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
		//
		// Terminal： forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、
		// max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、
		// iterator
		//
		// Short-circuiting： anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、
		// limit
		// map/flatMap

		// 清单 7. 转换大写
		String[] wordList = new String[] { "a", "b", "c" };
		Arrays.asList(wordList).stream().map(String::toUpperCase).forEach(System.out::println);

		// 清单 8. 平方数
		List<Integer> nums = Arrays.asList(1, 2, 3, 4);
		List<Integer> squareNums = nums.stream().map(n -> n * n).collect(Collectors.toList());
		// 这段代码生成一个整数 list 的平方数 {1, 4,9,16}。
		System.out.println("-----------------分割线-----------------");
		// 清单 9. 一对多
		Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1, 2), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
		inputStream.flatMap((childList) -> childList.stream()).forEach(System.out::println);
		// flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，
		// 最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字。
		System.out.println("-----------------分割线-----------------");

		// filter
		// 清单 10. 留下偶数
		Integer[] sixNums = { 1, 2, 3, 4, 5, 6 };
		Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
		// 经过条件“被 2 整除”的 filter，剩下的数字为 {2, 4, 6}。

		// 清单 11. 把单词挑出来
		String path = StreamTest.class.getClassLoader().getResource("").getPath();
		System.out.println(path + "words.txt");
		BufferedReader reader = new BufferedReader(new FileReader(path + "words.txt"));

		List<String> output = reader.lines().flatMap(line -> Stream.of(line.split(",")))
				.filter(word -> word.length() > 0).collect(Collectors.toList());
		System.out.println(output.toString());

		// 对一个文本遍历，找出单词姓名。可以看出来，forEach 是为 Lambda 而设计的，保持了最紧凑的风格。而且 Lambda
		// 表达式本身是可以重用的，非常方便。
		// 当需要为多核系统优化时，可以
		// parallelStream().forEach()，只是此时原有元素的次序没法保证，并行的情况下将改变串行时操作的行为，
		// 此时 forEach 本身的实现不需要调整，而 Java8 以前的 for 循环 code 可能需要加入额外的多线程逻辑。
		// 但一般认为，forEach 和常规 for 循环的差异不涉及到性能，它们仅仅是函数式风格与传统 Java 风格的差别。
		// 另外一点需要注意，forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个
		// Stream 进行两次 terminal 运算。下面的代码是错误的：
		// stream.forEach(element -> doOneThing(element));
		// stream.forEach(element -> doAnotherThing(element));

		// 相反，具有相似功能的 intermediate 操作 peek 可以达到上述目的。如下是出现在该 api javadoc 上的一个示例。
		Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3)
				.peek(e -> System.out.println("Filtered value: " + e)).map(String::toUpperCase)
				.peek(e -> System.out.println("Mapped value: " + e)).collect(Collectors.toList());

		// forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环

		// 清单 15. reduce 的用例
		// 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面
		// Stream 的第一个、第二个、第 n 个元素组合。
		// 从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。例如 Stream 的 sum
		// 就相当于
		// Integer[] integers = { 1, 2, 3, 4, 5, 6 };
		// Integer sum = integers.reduce(0, (a, b) -> a+b);
		// 或Integer sum = integers.reduce(0, Integer::sum);
		// 也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。

		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		// 求最小值，minValue = -3.0
		double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		// 求和，sumValue = 10, 有起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		// 求和，sumValue = 10, 无起始值
		sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);
		// 上面代码例如第一个示例的 reduce()，第一个参数（空白字符）即为起始值，第二个参数（String::concat）为
		// BinaryOperator。这类有起始值的 reduce() 都返回具体的对象。
		// 而对于第四个示例没有起始值的 reduce()，由于可能没有足够的元素，返回的是 Optional，请留意这个区别。

		// limit/skip
		// limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）。
		new StreamTest().testLimitAndSkip();
		
		//清单 17. limit 和 skip 对 sorted 后的运行次数无影响
		List<Person> persons = new ArrayList();
		 for (int i = 1; i <= 5; i++) {
		 Person person =new StreamTest().new Person(i, "name" + i);
		 persons.add(person);
		 }
		List<Person> personList2 = persons.stream().sorted((p1, p2) -> 
		p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
		System.out.println(personList2);
		//即虽然最后的返回元素数量是 2，但整个管道中的 sorted 表达式执行次数没有像前面例子相应减少。
		//最后有一点需要注意的是，对一个 parallel 的 Steam 管道来说，如果其元素是有序的，那么 limit 操作的成本会比较大，因为它的返回对象必须是前 n 个也有一样次序的元素。
		//取而代之的策略是取消元素间的次序，或者不要用 parallel Stream

		//sorted
		//对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip 甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。我们对清单 14 进行优化：
		//清单 18. 优化：排序前进行 limit 和 skip
		List<Person> personsTwo = new ArrayList();
		 for (int i = 1; i <= 5; i++) {
		 Person person = new StreamTest().new Person(i, "name" + i);
		 persons.add(person);
		 }
		List<Person> personListTwo = personsTwo.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
		System.out.println(personList2);
		//当然，这种优化是有 business logic(业务逻辑)上的局限性的：即不要求排序后再取值。
		
		//min/max/distinct
		//min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为 O(n)，而 sorted 的成本是 O(n log n)。
		//同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
		//清单 19. 找出最长一行的长度
		int longest = reader.lines().
		 mapToInt(String::length).
		 max().
		 getAsInt();
		reader.close();
		System.out.println(longest);
		//下面的例子则使用 distinct 来找出不重复的单词。
		
		//清单 20. 找出全文的单词，转小写，并排序
//		List<String> words = reader.lines().
//				 flatMap(line -> Stream.of(line.split(" "))).
//				 filter(word -> word.length() > 0).
//				 map(String::toLowerCase).
//				 distinct().
//				 sorted().
//				 collect(Collectors.toList());
//		reader.close();
//				System.out.println(words);
		
		
		//Match
		//Stream 有三个 match 方法，从语义上说：
		//allMatch：Stream 中全部元素符合传入的 predicate，返回 true
		//anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
		//noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
		//它们都不是要遍历全部元素才能返回结果。例如 allMatch 只要一个元素不满足条件，就 skip 剩下的所有元素，返回 false。
		//对清单 13 中的 Person 类稍做修改，加入一个 age 属性和 getAge 方法。
		List<Person> personMatch = new ArrayList();
		personMatch.add(new StreamTest().new Person(1, "name" + 1, 10));
		personMatch.add(new StreamTest().new Person(2, "name" + 2, 21));
		personMatch.add(new StreamTest().new Person(3, "name" + 3, 34));
		personMatch.add(new StreamTest().new Person(4, "name" + 4, 6));
		personMatch.add(new StreamTest().new Person(5, "name" + 5, 55));
		boolean isAllAdult = personMatch.stream().allMatch(p -> p.getAge() > 18);
		System.out.println("All are adult? " + isAllAdult);
		boolean isThereAnyChild = persons.stream().anyMatch(p -> p.getAge() < 12);
		System.out.println("Any child? " + isThereAnyChild);
		// object case
		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		// 建立操作流水线: 选出头三个高热量的 菜肴
		List<String> threeHighCaloricDishNames = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName)
				.limit(3).collect(toList());
		System.out.println(threeHighCaloricDishNames.toString());

		// 建立操作流水线: 选出头三个高热量的 菜肴 并打印
		menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).limit(3).collect(toList())
				.forEach(System.out::println);

		// forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
		menu.stream().filter(p -> p.getType() == Dish.Type.OTHER).forEach(p -> System.out.println(p.getName()));
	}

	/**
	 * 清单 16. limit 和 skip 对运行次数的影响 这是一个有 10，000 个元素的 Stream，但在 short-circuiting操作 limit 和 skip 的作用下
	 * 管道中 map 操作指定的 getName() 方法的执行次数为 limit 所限定的 10 次，
	 * 而最终返回结果在跳过前 3 个元素后只有后面 7 个返回。有一种情况是 limit/skip 无法达到 short-circuiting 目的的，就是把它们放在 Stream 的排序操作后，
	 * 原因跟 sorted 这个 intermediate 操作有关：此时系统并不知道Stream 排序后的次序如何，所以 sorted 中的操作看上去就像完全没有被 limit 或者 skip 一样。
	 */
	public void testLimitAndSkip() {
		List<Person> persons = new ArrayList();
		for (int i = 1; i <= 10000; i++) {
			Person person = new Person(i, "name" + i);
			persons.add(person);
		}
		List<String> personList2 = persons.stream().map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
		System.out.println(personList2);
	}

	/**
	 * 在更复杂的 if (xx != null) 的情况中，使用 Optional 代码的可读性更好，而且它提供的是编译时检查， 能极大的降低 NPE
	 * 这种 Runtime Exception 对程序的影响，或者迫使程序员更早的在编码阶段处理空值问题，而不是留到运行时再发现和调试。 Stream
	 * 中的 findAny、max/min、reduce 等方法等返回 Optional 值。还有例如 IntStream.average() 返回
	 * OptionalDouble 等等。
	 * 
	 * @param text
	 */
	public static void print(String text) {
		// Java 8
		Optional.ofNullable(text).ifPresent(System.out::println);
		// Pre-Java 8
		if (text != null) {
			System.out.println(text);
		}
	}

	public static int getLength(String text) {
		// Java 8
		return Optional.ofNullable(text).map(String::length).orElse(-1);
		// Pre-Java 8
		// return if (text != null) ? text.length() : -1;
	};

	private class Person {
		public int no;
		private String name;
		private int age;

		public Person(int no, String name) {
			this.no = no;
			this.name = name;
		}
		
		public Person(int no, String name,int age) {
			this.no = no;
			this.name = name;
			this.age=age;
		}

		public int getAge() {
			System.out.println(age);
			return age;
		}

		public String getName() {
			System.out.println(name);
			return name;
		}
	}
}
