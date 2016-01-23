package com.test;
import java.lang.annotation.Annotation;
import com.annotation.RequestMapping;
import com.annotation.TestAnnotation;
import com.bean.BaseFruit;
public class AnnotationMothod {
	
	public static void main(String[] args)  {
		
		System.out.println(System.getProperty("java.io.tmpdir"));
//		System.out.println(Apple.class.isAnnotationPresent(TestAnnotation.class)); 判断一个类是否被一个Annotation修饰
		Class<BaseFruit> baseFruit=BaseFruit.class;
		//java8新增获取方式getDeclaredAnnotationsByType 经过测试不支持原注解@Inherited(继承)
		TestAnnotation[] testannotation =baseFruit.getDeclaredAnnotationsByType(TestAnnotation.class);
		for (TestAnnotation testAnnotation2 : testannotation) {
			System.out.println(testAnnotation2.name());
		}
		try{
		Annotation[] arry=Class.forName("com.bean.Apple").getAnnotations();
		for (Annotation annotation : arry) {
		System.out.println(annotation);	
			if(annotation instanceof TestAnnotation){
				System.out.println(((TestAnnotation) annotation).age());;
				System.out.println(((TestAnnotation) annotation).name());;
			}
		}
		
		Annotation[] arrys=Class.forName("com.action.LoginAtion").getMethod("loginUI").getAnnotations();
		for (Annotation annotation : arrys) {
				System.out.println(((RequestMapping) annotation).value());
		}
		
		
		Annotation[] arry1=Class.forName("com.action.LoginAtion").getAnnotations();
		for (Annotation annotation : arry1) {
				System.out.println(((RequestMapping) annotation).value());
		}
		
		}catch(Exception e){
			System.out.println(e);
		}
		
		
//		Annotation[] arrys=Class.forName("com.annotation.TestAnnotation").getAnnotations();
//		for (Annotation annotation : arrys) {
//		System.out.println(annotation);	
//		}
		
		
	}

}
