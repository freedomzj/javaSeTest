package com.test;

import com.util.DownUtil;

public class MultiThreadDown {

	
	public static void main(String[] args) throws Exception {
		final DownUtil downUtil=new DownUtil("http://www.firmpal.com/wb_26fbda17c646984d.txt","D://car.txt",1);
		//开始下载
		downUtil.download();
		new Thread(() ->{
			while(downUtil.getCompleteRate()<1){
				//每隔0.1秒查询一次任务的完成进度
				//GUI程序中科根据该进度来绘制进度条
				System.out.println("已完成："+downUtil.getCompleteRate());
				try{
					Thread.sleep(1000);
				}catch(Exception ex){}
			}
		}).start();
	}
}
