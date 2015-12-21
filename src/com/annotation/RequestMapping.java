package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)//save in runtime
@Target(value={ElementType.METHOD,ElementType.TYPE}) //Can be modified METHOD
public @interface RequestMapping {
	
	String value();
	
}
