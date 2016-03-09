package com.demo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SocketDemoTest {

	private static JTextField txtIpAddress;
	private static JButton btnStart;
	private static JTextArea ta;
	private static JTextField txtMsg;
	private static JButton sendMsg;
	private ServerSocket serverSocket = null;
	private static Map<Integer, String> userMap=new HashMap<Integer, String>();
	public static  List<Socket> socketList = Collections
			.synchronizedList(new ArrayList<Socket>());
	private static JLabel lblCount;
	
	private static JPanel left_panel;

	public static void main(String[] args) {
		MyFirstJf jf = new MyFirstJf("��������");
		// ���ò��ַ�ʽ
		jf.setLayout(new BorderLayout(30, 5));

		// ����
		JPanel top_panel = new JPanel();
		top_panel.add(new JLabel("ip��ַ"));
		btnStart = new JButton("����������");
		btnStart.addActionListener(new SocketDemoTest().new BtnStart());
		txtIpAddress = new JTextField(10);
		txtIpAddress.setText("192.168.3.86");
		txtIpAddress.setEnabled(false);
		top_panel.add(txtIpAddress);
		top_panel.add(btnStart);

		// ��Ϣ���g����
		JPanel p = new JPanel();
		ta = new JTextArea();
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		p.setLayout(new BorderLayout());
		p.add(scrollPane, BorderLayout.CENTER);
		JPanel foot_panel = new JPanel();

		left_panel=new JPanel();
		left_panel.add(new JLabel("��ǰ�����û��б�"));
		//�ײ�
		foot_panel.add(new JLabel("��ǰ��������:"));
		lblCount=new JLabel("0");
		lblCount.setForeground(Color.red);
		foot_panel.add(lblCount);
		foot_panel.add(new JLabel("��Ϣ"));
		txtMsg = new JTextField(20);
		foot_panel.add(txtMsg);

		sendMsg = new JButton("����");
		foot_panel.add(sendMsg);
		sendMsg.addActionListener(new SocketDemoTest().new BtnSendMsg());
		sendMsg.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent evt) {  
			        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
			        	JOptionPane.showMessageDialog(null, "aaa");
			        }  
			    }  
		});
		
		jf.add(top_panel, BorderLayout.NORTH);
		jf.add(p);
		jf.add(left_panel,BorderLayout.WEST);
		jf.add(foot_panel, BorderLayout.SOUTH);

		jf.setVisible(true);

	}

	class BtnSendMsg implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(socketList!=null&&socketList.size()>0){
				if(!"".equals(txtMsg.getText())){
					new SocketDemoTest().new SendMsgThread().run();
				}else{
					JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�������·��ͣ�");
				}
			}else{
				JOptionPane.showMessageDialog(null, "��ǰû�пͻ����������Ժ�����");
			}
			
			
		}

	}

	class BtnStart implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startServer();
		}

	}

	public void startServer() {
		try {
			serverSocket = new ServerSocket(9999);
			ta.append("�����������ɹ�����������\n");
		}
		catch (BindException e) {
			ta.append("����������ʧ�ܶ˿ڱ�ռ�á���������\n");
		}
		catch (IOException e) {
			ta.append("����������ʧ�ܡ���������\n");
		}
		//�����̵߳ȴ�����
		new MultiSocketServer().start();
		
//		for (Object obj : userMap.keySet()) {
//			left_panel.add(new Label(userMap.get(obj)));
//		}
		btnStart.setEnabled(false);
	}

	private class MultiSocketServer extends Thread {

		private Socket socket = null;
		@Override
		public void run() {

			try {
				while (true) {
					socket = serverSocket.accept(); // ���������������ȡ��һ������
					// �ر�socket�ӳ�,�Ի�ȡ������ٶ�
					socket.setTcpNoDelay(true);
					socketList.add(socket);// ÿ�δ���һ���������ӵ�������
					lblCount.setText(String.valueOf(socketList.size()));
//					ClientDemo.lblCount.setText(String.valueOf(socketList.size()));
					//��ÿ���������˵һ�件ӭ��
					String readerLine="";
					DataInputStream dis=new DataInputStream(socket.getInputStream());
					DataOutputStream dos = new DataOutputStream(
							socket.getOutputStream());
					readerLine=	dis.readUTF();
					dos.writeUTF("������˵:��ӭ��"+readerLine+"\n");
					userMap.put(socket.getPort(), readerLine);
					
					ta.append(readerLine+socketList.size()+"����ɹ��� ip��ַΪ"
					 + socket.getInetAddress() + " :�Ӷ˿�Ϊ�� " + socket.getPort()+"\n");
					//�������û�����������Ϣ
					for (Socket s : socketList) {
						if(s!=socket){
							DataOutputStream ds=new DataOutputStream(s.getOutputStream());
							ds.writeUTF("ϵͳ��Ϣ"+"��"+readerLine+"�����������ң�"+"\n");
						}
						
					}
					//�������Կͻ��˵���Ϣ
					new SocketDemoTest().new ReaderThread(socket).start();
					/*String readerLine="";
						//����socketList�е�ÿ��Socket
						//��������������ÿ��Socket����һ��
						for (Socket s :socketList) {
							try{
								DataInputStream dis=new DataInputStream(s.getInputStream());
								readerLine=	dis.readUTF();
								dis.close();
								
								//��������쳣���������socket��Ӧ�Ŀͻ��˹رղ���ʱɾ�������е�����
							}catch(IOException e){
								socketList.remove(socket);
							}
						}*/
						
					// ��ȡ�ͻ���������ַ�
					
				}
				
				

				// ���պͷ�������
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		

		
		

	}
	
	
	private class ReaderThread extends Thread{
		private Socket socket;
		public ReaderThread( Socket socket){
			this.socket=socket;
		}
		@Override
		public void run() {
			while(true){
				try {
					String readLine="";
					DataInputStream ds=new DataInputStream(socket.getInputStream());
					readLine=ds.readUTF();
					ta.append(readLine);
					for (Socket s : socketList) {
						if(s!=socket){
							DataOutputStream dos=new DataOutputStream(s.getOutputStream());
							dos.writeUTF(readLine);
						}
						
					}
					//�ѽ��ܵ�����Ϣ���͸������û�
//					for (Socket socket : socketList) {
//						DataInputStream ds=new DataInputStream(socket.getInputStream());
//						try{
//							System.out.println(socket.isClosed());
//							if(socket.isClosed()&&ds.readUTF()!=null)
//							
//							else
//								break;
//						}catch(Exception ex){
//						}
//					}
					
				} catch (IOException e) {
					//ͨ���쳣�Ƴ�������
					socketList.remove(socket);
					
					lblCount.setText(String.valueOf(socketList.size()));
//					txtMsg.setText(userMap.get(socket.getPort())+"�˳��������ң�������");
//					userMap.remove(socket.getPort());
//					
//					new SocketDemoTest().new SendMsgThread ().start();
				}
			}
		}
	}
	private class SendMsgThread extends Thread{
		@Override
		public void run() {
			try {
//			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				ta.append("��������Ϣ��  "+txtMsg.getText()+"\n");
//			System.out.println(socketList);
			for (Socket socket : socketList) {
				DataOutputStream ds=new DataOutputStream(socket.getOutputStream());
				ds.writeUTF("��������Ϣ��  "+txtMsg.getText()+"\n");
			}
			//ѭ����������Ϣ
			txtMsg.setText("");
			} catch (IOException e) {
				ta.append("�����������쳣����ʧ��");
			}
			
		}
	}

}
