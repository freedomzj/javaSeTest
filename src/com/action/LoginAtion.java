package com.action;

import com.annotation.RequestMapping;

@RequestMapping("/front")
public class LoginAtion {
	
	
	@RequestMapping("/login.htm")
	public String loginUI(){
		System.out.println("����annotationʵ��url��ת����");
		return "loginUI";
	}
}
