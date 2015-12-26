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
				//����socketList�е�ÿ��Socket
				//��������������ÿ��Socket����һ��
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
			//��������쳣���������socket��Ӧ�Ŀͻ��˹رղ���ʱɾ�������е�����
		}catch(IOException e){
			MultiSocketServer.socketList.remove(socket);
		}
		return null;
	}

	
	
}
