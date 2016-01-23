package com.bean;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Apple extends BaseFruit {
	
	private String name;
	
	public Apple(String name) {
		this.name = name;
	}

	public  void doApple() {
		System.out.println("��ʼ�ӹ�");
		Timer timer=new Timer();
		timer.schedule(new MyTask(), new Date(),5000);
	}
	
	class MyTask extends TimerTask{

		@Override
		public void run() {
			System.out.println("�ӹ�"+name);
			
		}
		
	}

}
