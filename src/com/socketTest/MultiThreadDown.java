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
				//ÿ��1���ѯһ���������ɽ���
				//GUI�����пɸ��ݸý��������ƽ�����
				System.out.println("����ɣ�"+downUtil.getCompleteRate());
				try{
					Thread.sleep(1000);
				}catch(Exception ex){}
			}
			endTime=refFormatNowDate(System.currentTimeMillis());
			System.out.println("�ļ���С"+fileSize/1024/1024+"/mb"+"���ؿ�ʼʱ�䣺"+startTime+"���ؽ���ʱ�䣺"+endTime);
		}).start();
		
		
	}
	
	public static String refFormatNowDate(long time) {
		  Date nowTime = new Date(time);
		  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		  String retStrFormatNowDate = sdFormatter.format(nowTime);
		  return retStrFormatNowDate;
		}
}
