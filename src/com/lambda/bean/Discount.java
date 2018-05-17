package com.lambda.bean;

import java.math.BigDecimal;

public class Discount {

	public enum Code {
		percentage;
	}

	/**
	 * 将折扣代码应用于商品最初 的原始价格
	 * 
	 * @param quote
	 * @return
	 */
	public static String applyDiscount(Quote quote) {
		return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
	}

	private static double apply(double price, Code code) {
		delay();
		return format(price * (100 - 80) / 100);// 模 拟 Discount
	}

	private static double format(double d) {
		BigDecimal bigDecimal=new BigDecimal(d);
		return bigDecimal.floatValue();
	}

	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
