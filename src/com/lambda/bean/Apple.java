package com.lambda.bean;

public class Apple {
	
	private String color;
	
	private Integer width;
	
	public Apple(){
		
	}
	
	public Apple(String color, Integer width) {
		this.color = color;
		this.width=width;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public static boolean isGreenApple(Apple apple) {
	    return "green".equals(apple.getColor());
	}
	
	public static boolean isHeavyApple(Apple apple){
		return  180>apple.getWidth();
	}
	

}
