package com.multiSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient {

	
	public static void main(String[] args) throws Exception {
		Socket s=new Socket("192.168.2.102",30001);
		System.out.println("��������");
		//�ͻ�������ClientThread�̲߳��ϵĶ�ȡ���Է�����������
		new Thread(new ClientThread(s)).start();
		//��ȡ��Socket��Ӧ�������
		PrintStream ps=new PrintStream(s.getOutputStream());
		String line=null;
		//���ϵĶ�ȡ��������
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while((line=br.readLine())!=null){
			ps.print(line);
		}
	}
}
