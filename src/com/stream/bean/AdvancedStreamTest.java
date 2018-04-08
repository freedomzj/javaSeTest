package com.stream.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @see https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 * @author 陈 争云, 占 宇剑, 和 司 磊
 *
 */
@SuppressWarnings("all")
public class AdvancedStreamTest {

	public static void main(String[] args) {
		// Stream.generate
		// 通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的
		// Stream，或者需要前后元素间维持着某种状态信息的 Stream。
		// 把 Supplier 实例传递给 Stream.generate() 生成的 Stream，默认是串行（相对 parallel
		// 而言）但无序的（相对 ordered 而言）。
		// 由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。
		// 清单 22. 生成 10 个随机整数
		Random seed = new Random();
		Supplier<Integer> random = seed::nextInt;
		Stream.generate(random).limit(10).forEach(System.out::println);
		// Another way
		IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10).forEach(System.out::println);
		// Stream.generate() 还接受自己实现的
		// Supplier。例如在构造海量测试数据的时候，用某种自动的规则给每一个变量赋值；或者依据公式计算 Stream
		// 的每个元素值。这些都是维持状态信息的情形。
		Stream.generate(new AdvancedStreamTest().new PersonSupplier()).limit(10)
				.forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));

		// Stream.iterate
		// iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。然后种子值成为 Stream
		// 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
		// 清单 24. 生成一个等差数列
		Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
		// 输出结果：
		// 0 3 6 9 12 15 18 21 24 27
		// 与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小。

		// java.util.stream.Collectors 类的主要作用就是辅助进行各类有用的 reduction 操作，例如转变输出为
		// Collection，把 Stream 元素进行归组。
		// groupingBy/partitioningBy
		// 清单 25. 按照年龄归组
		Map<Integer, List<Person>> personGroups = Stream.generate(new AdvancedStreamTest().new PersonSupplier())
				.limit(100).collect(Collectors.groupingBy(Person::getAge));
		Iterator it = personGroups.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
			System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
		}
		// 清单 26. 按照未成年人和成年人归组
		Map<Boolean, List<Person>> children = Stream.generate(new AdvancedStreamTest().new PersonSupplier()).limit(100)
				.collect(Collectors.partitioningBy(p -> p.getAge() < 18));
		System.out.println("Children number: " + children.get(true).size());
		System.out.println("Adult number: " + children.get(false).size());
		//总之，Stream 的特性可以归纳为：
		//不是数据结构
		//它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
		//它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
		//所有 Stream 的操作必须以 lambda 表达式为参数
		//不支持索引访问
		//你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
		//很容易生成数组或者 List
		//惰性化
		//很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
		//Intermediate 操作永远是惰性化的。
		//并行能力
		//当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
		//可以是无限的
		//集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
	}

	private class PersonSupplier implements Supplier<Person> {
		private int index = 0;
		private Random random = new Random();

		@Override
		public Person get() {
			return new Person(index++, "StormTestUser" + index, random.nextInt(100));
		}
	}

	private class Person {
		public int no;
		private String name;
		private int age;

		public Person(int no, String name) {
			this.no = no;
			this.name = name;
		}

		public Person(int no, String name, int age) {
			this.no = no;
			this.name = name;
			this.age = age;
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
