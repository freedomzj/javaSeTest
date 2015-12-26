package com.socketTest;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.util.DownUtil;

public class MultiThreadDown {
	
	
	private static  String endTime;
	private static String startTime;
	private static int fileSize;
	public static void main(String[] args) throws Exception {
		startTime=refFormatNowDate(System.currentTimeMillis());
		final DownUtil downUtil=new DownUtil("http://www.gvglh.com/upload/carstore/bc186900-57fa-48ef-a8f6-73aa1e426366.jpg",
		"D://car.html",1);
		downUtil.download();
		fileSize=downUtil.getFileSize();
		new Thread(() ->{
			while(downUtil.getCompleteRate()<1){
				//每隔1秒查询一次任务的完成进度
				//GUI程序中可根据该进度来绘制进度条
				System.out.println("已完成："+downUtil.getCompleteRate());
				try{
					Thread.sleep(1000);
				}catch(Exception ex){}
			}
			endTime=refFormatNowDate(System.currentTimeMillis());
			System.out.println("文件大小"+fileSize/1024/1024+"/mb"+"下载开始时间："+startTime+"下载结束时间："+endTime);
		}).start();
		
		
	}
	
	public static String refFormatNowDate(long time) {
		  Date nowTime = new Date(time);
		  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		  String retStrFormatNowDate = sdFormatter.format(nowTime);
		  return retStrFormatNowDate;
		}
}
