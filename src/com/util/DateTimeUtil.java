package com.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class DateTimeUtil {
	 private static LocalDate localDate = LocalDate.now();
	public static void main(String[] args) {
		LocalTime localTime = LocalTime.now();
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("输入改变前的日期:" + localDate.toString() + "-"
				+ localTime.toString() + "localDateTime" + localDateTime);
		localDateTime = localDateTime.withYear(2017);// 改变年数
		//lambda 方式更改日期
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
	
	//匿名内部类方式实现更改日期
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
	
	
	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static int getYear() {
		return localDate.getYear();
	}

}
