package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value =RetentionPolicy.RUNTIME)//save runtime
@Target(value=ElementType.TYPE)//Can be modified class interface  and emun
//ElementType.ANNOTATION_TYPE//Can be modified ANNOTATION
@Inherited
public @interface TestAnnotation {
	String name();
	int age();
}
