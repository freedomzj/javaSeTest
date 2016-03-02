package com.test;
import java.io.FileReader;
import java.io.LineNumberReader;
public class SynchronizedTest {
	
	
		public static void main(String[] args) throws Exception {
			
			LineNumberReader lineNumberReader=new LineNumberReader(new FileReader("D://dom4j.xml"));
			String str="";
			while((str=(lineNumberReader.readLine()))!=null){
//				str=new String(str.getBytes("GBK"),"utf-8");
				System.out.println(str);
				if(str.indexOf("李四")!=-1){
					System.out.println(lineNumberReader.getLineNumber());
					break;
					
				}
			}
			lineNumberReader.close();
		}
	
	
	
	
}

