package com.util;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownUtil {
	// 定义下载资源的路径
	private String path;
	// 指定所下载的文件德尔保存位置
	private String targetFile;
	// 定义使用多少个线程下载资源
	private int threadNum;
	// 定义下载的线程对象
	private DownThread[] threads;
	// 定义下载文件的总大小
	private int fileSize;

	public DownUtil(String path, String targetFile, int threadNum) {
		this.path = path;
		this.threadNum = threadNum;
		threads = new DownThread[threadNum];
		this.targetFile = targetFile;
	}

	public void download() throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "image/gif, image/jpeg ,image/pjpeg"
				+ "application/x-shockwave-flash, application/xaml+xml,"
				+ "application/vnd.ms-xpsdocument, application/x-ms-xbap,"
				+ "application/x-ms-application, application/vnd.ms-excel"
				+ "application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Connection", "Keep-alive");
		// 得到文件大小
		fileSize = conn.getContentLength();
		conn.disconnect();
		int currentPartSize = fileSize / threadNum + 1;
		RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
		// 设置本地文件的大小
		file.setLength(fileSize);
		file.close();
		for (int i = 0; i < threadNum; i++) {
			// 计算每个线程下载的开始位置
			int startPos = i * currentPartSize;
			// 让每个线程使用一个RandomAccessFile进行下载
			RandomAccessFile currentPart = new RandomAccessFile(targetFile,
					"rw");
			// 定义该线程的下载位置
			currentPart.seek(startPos);
			// 创建下载线程
			threads[i] = new DownThread(startPos, currentPartSize, currentPart);
			// 启动下载此线程
			threads[i].start();
		}
	}

	// 获取下载的完成百分比
	public double getCompleteRate() {
		// 统计多个线程已经下载的总大小
		int sumSize = 0;
		for (int i = 0; i < threadNum; i++) {
			sumSize += threads[i].length;
		}
		// 返回已经完成的百分比
		return sumSize * 1.0 / fileSize;
	}

	private class DownThread extends Thread {

		private int startPos;

		private int currentPartSize;

		private RandomAccessFile currentPart;
		public int length;

		public DownThread(int startPos, int currentPartSize,
				RandomAccessFile currentPart) {
			this.startPos = startPos;
			this.currentPartSize = currentPartSize;
			this.currentPart = currentPart;
		}

		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty(
						"Accept",
						"image/gif, image/jpeg ,image/pjpeg"
								+ "application/x-shockwave-flash, application/xaml+xml,"
								+ "application/vnd.ms-xpsdocument, application/x-ms-xbap,"
								+ "application/x-ms-application, application/vnd.ms-excel"
								+ "application/vnd.ms-powerpoint, application/msword, */*");
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Charset", "UTF-8");
				InputStream inStream = conn.getInputStream();
				// 跳过startPos个字节，表明该线程只下载自己负责的那部分文件
				inStream.skip(this.startPos);
				byte[] buffer = new byte[1024];
				int hasRead = 0;
				while (length < currentPartSize && (hasRead=inStream.read(buffer))!=-1) {
					currentPart.write(buffer, 0, hasRead);
					// 累计改线程下载的总大小
					length += hasRead;
				}
				currentPart.close();
				inStream.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public DownThread[] getThreads() {
		return threads;
	}

	public void setThreads(DownThread[] threads) {
		this.threads = threads;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

}
