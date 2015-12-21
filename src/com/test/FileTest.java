package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileTest {
	
	public static void main(String[] args) {
		FileTest fileTest=new FileTest();
		fileTest.printFile1();
	}
	
	public void printFile(){
		FileInputStream fis=null;
		FileOutputStream fos=null;
		try{
			 fis=new FileInputStream("D:/zengjie.txt");
			 fos=new FileOutputStream("D:/newzengjie.txt");
			 byte[] bytes=new byte[1024];
			 int temp=0;
			 while((temp=fis.read(bytes))>0){
				 fos.write(bytes,0,temp);
			 }
		}catch(FileNotFoundException fe){
			System.out.println(fe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void printFile1() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("D:/newzengjie.txt");
			PrintStream ps=new PrintStream(fos);
			ps.print("sdfsdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void showFileDirectory(){
    	File[] file=File.listRoots();
		for (File file2 : file) {
			System.out.println(file2);
			
			if(file2.list()!=null){
				for (String fi : file2.list()) {
					System.out.println(fi);
					File filed=new File(file2+fi);
					if(filed.isDirectory()){
						if(filed.list()!=null){
							for (String file3 : filed.list()) {
								System.out.println(file3);	
								}
						}
						
					}
				}
			}
			
		}
    }
	  
	  

}
