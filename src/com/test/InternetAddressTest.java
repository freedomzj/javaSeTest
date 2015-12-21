package com.test;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
public class InternetAddressTest {

	
	
	public static void main(String[] args) throws Exception {
		InetAddress address=InetAddress.getByName("www.gvglh.com"); 
		 System.out.println( "��վ�Ƿ�ɴ�"+address.isReachable(2000));
		 System.out.println(address.getHostAddress());
		 InetAddress local=InetAddress.getByAddress(new byte[]{127,0,0,1});
		 System.out.println("�����Ƿ�ɴ�"+local.isReachable(2000));
		 //��ȡ��InetAddressʵ����Ӧ��ȫ�޶�����
		 System.out.println(local.getCanonicalHostName());
		 
		 String urlStr=URLEncoder.encode("����", "GBk");
		 System.out.println("ʹ��GBK��������-----------"+urlStr);
		 if(urlStr.equals(new String(urlStr.getBytes("GBk"), "GBk")))
         {
		  System.out.println("ʹ��GBK����");
         }
		 
		 String GBKkey=URLDecoder.decode(urlStr, "GBK");
		 System.out.println("ʹ��GBK��������---------"+GBKkey);
		 
		 
		 String Utf8key=URLDecoder.decode(urlStr, "utf-8");
		 System.out.println("ʹ��Utf-8��������---------"+Utf8key);
		 
		 
		 String isokey=URLDecoder.decode(urlStr, "iso-8859-1");
		 System.out.println("ʹ��iso-8859-1��������----------"+isokey);
		 
		 Random random=new Random();
		
		 for (int i = 0; i < 100; i++) {
			 System.out.println(random.nextInt(1000));
		}
		 
		 
		 
	}
}
