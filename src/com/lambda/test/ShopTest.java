package com.lambda.test;

import static java.util.stream.Collectors.toList;
import static com.lambda.bean.Quote.parse;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import com.lambda.bean.Discount;
import com.lambda.bean.Quote;
import com.lambda.bean.Shop;

/*
 * 调整线程池的大小
《Java并发编程实战》(http://mng.bz/979c)一书中,Brian Goetz和合著者们为线程池大小 的优化提供了不少中肯的建议。
这非常重要,如果线程池中线程的数量过多,最终它们会竞争 稀缺的处理器和内存资源,浪费大量的时间在上下文切换上。
反之,如果线程的数目过少,正 如你的应用所面临的情况,处理器的一些核可能就无法充分利用。Brian Goetz建议,线程池大 小与处理器的利用率之比可以使用下面的公式进行估算:
Nthreads = NCPU * UCPU * (1 + W/C)
其中: ❑NCPU是处理器的核的数目,可以通过Runtime.getRuntime().availableProce-
ssors()得到 ❑UCPU是期望的CPU利用率(该值应该介于0和1之间) ❑W/C是等待时间与计算时间的比率
 */

/*
 * 并行——使用流还是CompletableFutures? 目前为止,你已经知道对集合进行并行计算有两种方式:要么将其转化为并行流,利用map
这样的操作开展工作,要么枚举出集合中的每一个元素,创建新的线程,在Completable- Future内对其进行操作。后者提供了更多的灵活性,你可以调整线程池的大小,而这能帮助 你确保整体的计算不会因为线程都在等待I/O而发生阻塞。
我们对使用这些API的建议如下。 ❑如果你进行的是计算密集型的操作,并且没有I/O,那么推荐使用Stream接口,因为实
现简单,同时效率也可能是最高的(如果所有的线程都是计算密集型的,那就没有必要
创建比处理器核数更多的线程)。 ❑反之,如果你并行的工作单元还涉及等待I/O的操作(包括网络连接等待),那么使用
CompletableFuture灵活性更好,你可以像前文讨论的那样,依据等待/计算,或者 W/C的比率设定需要使用的线程数。
这种情况不使用并行流的另一个原因是,处理流的 流水线中如果发生I/O等待,流的延迟特性会让我们很难判断到底什么时候触发了等待。
 */
public class ShopTest {

	static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"),
			new Shop("BuyItAll"), new Shop("shop1"), new Shop("shop2"), new Shop("shop3"), new Shop("shop4"), new Shop("shop5"),
			new Shop("shop6"));

	/**
	 * 创建一个线 程池,线程 池中线程的 数目为100 和商店数目 二者中较小 的一个值
	 */
	private final static Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
			new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);// 使用守护线程——这种方式不会阻止程序
										// 的关停
					return t;
				}
			});

	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");
		// 执行更多任务,比如查询其他商店
		// doSomethingElse();
		// 在计算商品价格的同时
		try {
			double price = futurePrice.get();
			System.out.printf("Price is %.2f%n", price);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
		// 我们看到这段代码中,客户向商店查询了某种商品的价格。由于商店提供了异步API,该次 调用立刻返回了一个Future对象,
		// 通过该对象客户可以在将来的某个时刻取得商品的价格。这 种方式下,客户在进行商品价格查询的同时,还能执行一些其他的任务,
		// 比如查询其他家商店中 商品的价格,不会呆呆地阻塞在那里等待第一家商店返回请求的结果。
		// 最后,如果所有有意义的 工作都已经完成,客户所有要执行的工作都依赖于商品价格时,再调用Future的get方法。
		// 执行 了这个操作后,客户要么获得Future中封装的值(如果异步任务已经完成),要么发生阻塞,直 到该异步任务完成,期望的值能够访问。
		start = System.nanoTime();
		System.out.println(findPrice("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}

	// 4个商店耗时4022 ms
	// 10个商店耗时10052 ms
	public static List<String> findStreamPrices(String product) {
		return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(toList());
	}

	// 4个商店耗时1017 ms
	// 10个商店耗时3026 ms
	public static List<String> findParallelStreamPrices(String product) {
		return shops.parallelStream()
				.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
				.collect(toList());
	}

	// 4个商店耗时2022 ms
	// 10个商店耗时4021 ms
	public static List<String> findFuturePrices(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product)))
				.collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(toList());
	}
	// CompletableFuture版本的程序似乎比并行流版本的程序还快那么一点儿。但是最后这个
	// 版本也不太令人满意。比如,如果你试图让你的代码处理9个商店,并行流版本耗时3143毫秒,
	// 而CompletableFuture版本耗时3009毫秒。
	// 它们看起来不相伯仲,究其原因都一样:它们内部 采用的是同样的通用线程池,默认都使用固定数目的线程,具体线程数取决于Runtime.
	// getRuntime().availableProcessors()的返回值。
	// 然而,CompletableFuture具有一定的
	// 优势,因为它允许你对执行器(Executor)进行配置,尤其是线程池的大小,让它以更适合应用需求的方式进行配置,满足程序的要求,而这是并行流API无法提供的

	// Done in 1015 msecs
	public static List<String> findCompletableFuturePrices(String product) {
		// 注意,你现在正创建的是一个由守护线程构成的线程池。Java程序无法终止或者退出一个正
		// 在运行中的线程,所以最后剩下的那个线程会由于一直等待无法发生的事件而引发问题。
		// 与此相 反,如果将线程标记为守护进程,意味着程序退出时它也会被回收。这二者之间没有性能上的差
		// 异。现在,你可以将执行器作为第二个参数传递给supplyAsync工厂方法了。
		// 比如,你现在可 以按照下面的方式创建一个可查询指定商品价格的CompletableFuture对象:
		List<CompletableFuture<String>> priceFutures = shops
				.stream().map(shop -> CompletableFuture
						.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
				.collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(toList());
	}

	// 对多个异步任务进行流水线操作
	// 查询10个商店并且对商品进行打8折最后返回金额 耗时2034 ms
	public static List<String> findPrice(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getFormatPrice(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(
						quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
				.collect(toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(toList());
	}

	public List<String> findPrices(String product) {
		return shops.stream().map(shop -> shop.getFormatPrice(product)).map(Quote::parse) // 在Quote对象中
																							// 对shop返回的字
																							// 符串进行转换
				.map(Discount::applyDiscount).collect(toList());// 联系Discount服
																// 务,为每个Quote
																// 申请折扣
	}
}
