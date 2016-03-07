package com.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class JavaIo {

	public static void main(String[] args) {
//		new JavaIo().new myTestIO().run();
		for(String fileName: args){
			new JavaIo().new DigestThread(fileName).start();
		}
		
	}

	private class myTestIO implements Runnable {

		@Override
		public void run() {
			File file = new File("logo.jpg");
			try (InputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream("D:\\logo.jpg");) {
				byte[] b = new byte[1024];
				int temp = 0;
				while ((temp = fis.read(b)) != -1) {
					fos.write(b, 0, temp);
				}
				fos.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(file.length());
		}

	}
	
	
	private class DigestThread extends Thread{
		private String fileName;
		public DigestThread(String fileName){
			this.fileName=fileName;
		}
		public void run() {
			try{
				FileInputStream in =new FileInputStream(fileName);
				MessageDigest sha=MessageDigest.getInstance("SHA-256");
				DigestInputStream din=new DigestInputStream(in,sha);
				while(din.read()!=-1){
					din.close();
					byte[] digest=sha.digest();
					StringBuilder result=new StringBuilder(fileName);
					result.append(":");
					result.append(DatatypeConverter.printHexBinary(digest));
					System.out.println(result);
				}
			}catch(IOException iex){
				System.out.println(iex.getMessage());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		}
	}

}
