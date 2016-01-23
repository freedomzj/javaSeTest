package com.web.crawlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class GetPostTest {

	
	/**
	 * 向指定url发送GET方式的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，格式满足namel=vale&nume2=value2的形式
	 * @return URL代表远程资源的响应
	 */
	public static String sendGet(String url,String param){
		String result ="";
		String urlName=url+"?"+param;
		try{
			URL realUrl=new URL(urlName);
			//打开和url直接的连接
			URLConnection conn=realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//建立实际的连接
			conn.connect();
			//获取收益的响应头字段
			Map<String, List<String>> map=conn.getHeaderFields();
			for(String key: map.keySet()){
				System.out.println(key+"--------->"+map.get(key));
			}
			BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while((line=in.readLine())!=null){
				result+="\n"+line;
			}
			
		}catch(Exception ex){
			System.out.println("发送GET请求异常"+ex);
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String sendPost(String url,String param){
		String result="";
		try{
			URL realUrl=new URL(url);
			//打开和URl直接的连接
			URLConnection conn=realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//发送post请求必须设置如下二行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			PrintWriter out=new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			BufferedReader in =new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while((line=in.readLine())!=null){
				result+="\n"+line;
			}
		}catch(Exception e){
			System.out.println("发送post请求异常！"+e);
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000000; i++) {
			String result= sendGet("http://localhost/user/list/1", "username=admin&password=123456");
			System.out.println(result);
		}
//				String result= sendGet("http://192.168.2.102:8080/user_checkLogin.action", "username=admin&password=123456");
//				System.out.println(result);
			
	}
	
	
}
