package com.demo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class ClientDemo {

	private static JTextField txtIpAddress;
	private static JButton btnStart;
	private static JTextArea ta;
	private static JTextField txtMsg;
	private static JButton sendMsg;
	private static JTextField txtPort;
	private static JTextField txtName;
	private static JButton btnEnd;
	public static Socket socket;
	public static JLabel lblCount;

	public static void main(String[] args) {
		MyFirstJf jf = new MyFirstJf("�ͻ���");
		// ���ò��ַ�ʽ
		jf.setLayout(new BorderLayout(30, 5));

		// ����
		JPanel top_panel = new JPanel();
		top_panel.add(new JLabel("ip��ַ"));
		btnStart = new JButton("���ӷ�����");
		btnStart.addActionListener(new ClientDemo().new BtnConnect());
		txtIpAddress = new JTextField(10);
		txtIpAddress.setText("192.168.3.86");
		top_panel.add(txtIpAddress);
		top_panel.add(new JLabel("�˿�"));
		txtPort = new JTextField(10);
		txtPort.setText("9999");
		top_panel.add(txtPort);

		top_panel.add(new JLabel("�û���"));
		txtName = new JTextField(10);
		top_panel.add(txtName);

		top_panel.add(btnStart);
		btnEnd = new JButton("�ر�����");
		btnEnd.setEnabled(false);
		btnEnd.addActionListener(new ClientDemo().new BtnCloseConnect());
		top_panel.add(btnEnd);

		// ��Ϣ���g����
		JPanel p = new JPanel(); 
		ta = new JTextArea();
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		p.setLayout(new BorderLayout());
		p.add(scrollPane, BorderLayout.CENTER);
		JPanel foot_panel = new JPanel();

		foot_panel.add(new JLabel("��ǰ��������:"));
		lblCount=new JLabel("0");
		lblCount.setForeground(Color.red);
		foot_panel.add(lblCount);
		
		foot_panel.add(new JLabel("��Ϣ"));
		txtMsg = new JTextField(20);
		foot_panel.add(txtMsg);

		sendMsg = new JButton("����");
		// ���÷��Ͱ�ť����
		sendMsg.setEnabled(false);
		txtMsg.setEditable(false);
		foot_panel.add(sendMsg);
		sendMsg.addActionListener(new ClientDemo().new BtnSendMsg(txtName
				.getText()));

		jf.add(top_panel, BorderLayout.NORTH);
		jf.add(p);
		jf.add(foot_panel, BorderLayout.SOUTH);
		jf.setVisible(true);
	}
	private class connectSocket extends Thread{
		@Override
		public void run() {
			try {
				socket = new Socket(txtIpAddress.getText(),
						Integer.valueOf(txtPort.getText()));

				// ��ȡ���������ص�����
				// DataInputStream di=new
				// DataInputStream(socket.getInputStream());
				
				// �Է���������ͨ�Ŵ����û�����ȥ
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				dos.writeUTF(txtName.getText());

				ta.append("���������ӳɹ�"+socket.isClosed()+"��ip��ַΪ +" + txtIpAddress.getText()
						+ "�˿�Ϊ" + txtPort.getText() + "\n");
				// �ر����ӷ�����������޸Ķ˿�
				//������Ϣ
				new ClientDemo().new ReaderThread(new DataInputStream(socket.getInputStream())).start();
				btnStart.setEnabled(false);
				btnEnd.setEnabled(true);
				// �������Ͱ�ť
				sendMsg.setEnabled(true);
				txtMsg.setEditable(true);
			} catch (NumberFormatException e1) {
				ta.append("�˿�ת���쳣" + "\n");
			} catch (UnknownHostException e1) {
				ta.append("����δ���ӣ��뿪��������" + "\n");
			} catch (IOException e1) {
				ta.append("���������쳣���뿪��������" + "\n");
			}
		}
	}
	
	private class ReaderThread extends Thread{
		DataInputStream dis = null;
		public ReaderThread(DataInputStream dis){
			this.dis = dis;
		}
		
		@Override
		public void run() {
			 try {
				 while(true){
						 ta.append(dis.readUTF());
					
				 }
			} catch (IOException e) {
			}catch (Exception ex){
			}
			
		}
		
	}
	
	private class BtnConnect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("".equals(txtIpAddress.getText())
					|| "".equals(txtPort.getText())
					|| "".equals(txtName.getText())) {
				JOptionPane.showMessageDialog(null, "�˿ڻ���ip��ַ����Ϊ�գ������û�������Ϊ��");
			} else {
				//�����߳�ͨ��
				new ClientDemo().new connectSocket().start();
			}
		}

	}

	private class BtnCloseConnect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				socket.close();
				ta.append("�رշ�����" + "\n");
				// �ر����ӷ�����������޸Ķ˿�
				btnStart.setEnabled(true);
				btnEnd.setEnabled(false);
			} catch (IOException e) {
				ta.append("�رշ�����");
			}
		}
		
	}

	private class BtnSendMsg implements ActionListener {

		private String userName;

		public BtnSendMsg(String userName) {
			this.userName = userName;
		}

		@Override
		
		public void actionPerformed(ActionEvent e) {
//			if (SocketDemoTest.socketList != null
//					&& SocketDemoTest.socketList.size() > 0) {
				if (!"".equals(txtMsg.getText())) {
					// �����߳̽��з�����Ϣ�����и����ı���
					new ClientDemo().new SendMsgThread(socket).run();;
				} else {
					JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�������·��ͣ�");
				}
//			} else {
//				JOptionPane.showMessageDialog(null, "��ǰû�пͻ����������Ժ�����");
//			}

		}

	}

	private class SendMsgThread extends Thread {
		private Socket socket;

		private SendMsgThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// BufferedWriter bw=new BufferedWriter(new
				// OutputStreamWriter(socket.getOutputStream()));
				DataOutputStream ds = new DataOutputStream(
						socket.getOutputStream());
				ta.append("��˵:" + txtMsg.getText() + "\n");
				ds.writeUTF(txtName.getText() + "˵:" + txtMsg.getText() + "\n");
				txtMsg.setText("");
			} catch (IOException e) {
				ta.append("�����������쳣����ʧ��");
			}

		}
	}

}
