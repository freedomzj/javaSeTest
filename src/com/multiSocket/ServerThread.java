package com.multiSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread  implements Runnable {

	private Socket socket;
	BufferedReader bfreader=null;
	
	public  ServerThread(Socket socket) throws IOException {
		this.socket=socket;
		bfreader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	@Override
	public void run() {
		try {
			String readerLine="";
			while((readerLine=readFromClient())!=null){
				//遍历socketList中的每个Socket
				//将读到的内容向每个Socket发送一次
				for (Socket s :MultiSocketServer.socketList) {
					PrintStream ps=new PrintStream(s.getOutputStream());
					ps.print(readerLine);
				}
			}
			bfreader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String readFromClient(){
		try{
			return bfreader.readLine();
			//如果捕获到异常，则表明该socket对应的客户端关闭并及时删除集合中的数据
		}catch(IOException e){
			MultiSocketServer.socketList.remove(socket);
		}
		return null;
	}

	
	
}
