package com.multiSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient {

	
	public static void main(String[] args) throws Exception {
		Socket s=new Socket("192.168.2.102",30001);
		System.out.println("开启连接");
		//客户端启动ClientThread线程不断的读取来自服务器的数据
		new Thread(new ClientThread(s)).start();
		//获取该Socket对应的输出流
		PrintStream ps=new PrintStream(s.getOutputStream());
		String line=null;
		//不断的读取键盘输入
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while((line=br.readLine())!=null){
			ps.print(line);
		}
	}
}
