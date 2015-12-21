package com.test;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
public class InternetAddressTest {

	
	
	public static void main(String[] args) throws Exception {
		InetAddress address=InetAddress.getByName("www.gvglh.com"); 
		 System.out.println( "网站是否可达"+address.isReachable(2000));
		 System.out.println(address.getHostAddress());
		 InetAddress local=InetAddress.getByAddress(new byte[]{127,0,0,1});
		 System.out.println("本机是否可达"+local.isReachable(2000));
		 //获取该InetAddress实例对应的全限定域名
		 System.out.println(local.getCanonicalHostName());
		 
		 String urlStr=URLEncoder.encode("曾杰", "GBk");
		 System.out.println("使用GBK编码曾杰-----------"+urlStr);
		 if(urlStr.equals(new String(urlStr.getBytes("GBk"), "GBk")))
         {
		  System.out.println("使用GBK编码");
         }
		 
		 String GBKkey=URLDecoder.decode(urlStr, "GBK");
		 System.out.println("使用GBK解码曾杰---------"+GBKkey);
		 
		 
		 String Utf8key=URLDecoder.decode(urlStr, "utf-8");
		 System.out.println("使用Utf-8解码曾杰---------"+Utf8key);
		 
		 
		 String isokey=URLDecoder.decode(urlStr, "iso-8859-1");
		 System.out.println("使用iso-8859-1解码曾杰----------"+isokey);
		 
		 Random random=new Random();
		
		 for (int i = 0; i < 100; i++) {
			 System.out.println(random.nextInt(1000));
		}
		 
		 
		 
	}
}
