package com.bean;

import com.annotation.TestAnnotation;

@TestAnnotation(age = 1, name = "baseFruit")
public class BaseFruit {
	
	public String color;
	
	private String weight;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	

}
