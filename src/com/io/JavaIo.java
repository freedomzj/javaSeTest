package com.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class JavaIo {

	@Test
	public void test() {
		writeFile();
	}

	public void writeFile() {
		File file = new File("logo.jpg");
		try {
			
			InputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream("D:\\logo.jpg");
			byte[] b = new byte[1024];
			int temp = 0;
			while ((temp = fis.read(b)) != -1) {
				fos.write(b, 0, temp);
			}
			fis.close();
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(file.length());
	}

}
