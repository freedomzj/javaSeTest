package com.stream.bean;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class StreamTest {

	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		LocalDateTime localDateTime = LocalDateTime.now();
		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		// 建立操作流水线: 选出头三个高热量的 菜肴
		List<String> threeHighCaloricDishNames = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName)
				.limit(3).collect(toList());

		System.out.println(threeHighCaloricDishNames + "头铁的输入改变前的日期:" + localDate.toString() + "-"
				+ localTime.toString() + "localDateTime" + localDateTime);
		localDateTime = localDateTime.withYear(2017);// 改变年数
		localDateTime = localDateTime.with(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;// 正常情况// 增加1天
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;// 如果当天是周 五,增加3天
			else if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;// 如果当天是周 六,增加2天
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);// 增加恰当的天数后,返回修改的日期
		});
		System.out.println("整理后的日期" + localDateTime.toString());

		LocalDate date = LocalDate.of(2014, 3, 18);
		String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
		String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
		System.out.println("s1:"+s1+"s2:"+s2);
		
		LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
		System.out.println("date1"+date1+"date2:"+date2);
	}

	public class NextWorkingDay implements TemporalAdjuster {
		@Override
		public Temporal adjustInto(Temporal temporal) {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));// 正常情况,
																				// 增加1天
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY)
				dayToAdd = 3;// 如果当天是周 五,增加3天
			else if (dow == DayOfWeek.SATURDAY)
				dayToAdd = 2;// 如果当天是周 六,增加2天
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);// 增加恰当的天数后,返回修改的日期

		}
	}

}
