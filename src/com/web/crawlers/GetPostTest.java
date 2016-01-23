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
	 * ��ָ��url����GET��ʽ������
	 * @param url ���������URL
	 * @param param �����������ʽ����namel=vale&nume2=value2����ʽ
	 * @return URL����Զ����Դ����Ӧ
	 */
	public static String sendGet(String url,String param){
		String result ="";
		String urlName=url+"?"+param;
		try{
			URL realUrl=new URL(urlName);
			//�򿪺�urlֱ�ӵ�����
			URLConnection conn=realUrl.openConnection();
			//����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//����ʵ�ʵ�����
			conn.connect();
			//��ȡ�������Ӧͷ�ֶ�
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
			System.out.println("����GET�����쳣"+ex);
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String sendPost(String url,String param){
		String result="";
		try{
			URL realUrl=new URL(url);
			//�򿪺�URlֱ�ӵ�����
			URLConnection conn=realUrl.openConnection();
			//����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			//����post��������������¶���
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
			System.out.println("����post�����쳣��"+e);
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
