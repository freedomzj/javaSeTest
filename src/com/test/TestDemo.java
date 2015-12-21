package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class TestDemo {
	Scanner sc=new Scanner(System.in);
	//定义一个棋盘大小
	private Integer  board_size=15;
	private String[][] board;//定义一个二维数组当棋盘
	
	public static void main(String[] args) throws IOException {
		//乘法口诀
		for (int i = 1; i <= 9; i++) {//外层控制打印行数
			for (int j = 1; j <= i; j++) {//内层控制打印次数
				System.out.print(i+"X"+j+"="+(i*j)+"\t");
			}
			System.out.println();
		}
		System.out.println("-------------------");
		//等腰三角形
		for (int i = 1; i <= 5; i++) {
			for (int j = 5-i; j > 0; j--) {
				System.out.print(" ");
			}
			for (int j =0 ; j < (i*2)-1; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		darwX(11);
		
		TestDemo testDemo=new TestDemo();
		int a=5,b=9;
		
		testDemo.swap(a, b);
		System.out.println("交换后的a{"+a+"}值为b值为{"+b+"}");
		testDemo.initBoard();
		testDemo.printBoard();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String str=new String();
		while((str=br.readLine())!=null){
			String[] strs=str.split(",");
			int xPos=Integer.parseInt(strs[0]);
			int yPos=Integer.parseInt(strs[1]);
			testDemo.board[xPos-1][yPos-1]="●";
			//随机生成电脑走的位置
			int jxPos=(int)(Math.random()*16);
			int jypox=(int)(Math.random()*16);
			testDemo.board[jxPos][jypox]="*";
			testDemo.printBoard();
			System.out.println("请输入你的下棋坐标x,y格式");
		}
	}
	
	
	public void initBoard(){
		board=new String[board_size][board_size];
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				board[i][j]="✚";
					
			}
		}
	}
	public void printBoard(){
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
    /**
     * 画出字母X
     * @param size:字母的大小
     */
    private static void darwX(int size) {
            size = size*2;
            for (int i = 0; i <= size/2; i++) {
                    for (int j = 0; j < i; j++) {
                            System.out.print(" ");
                    }
                    for (int k = i; k <= size-i; k=k+2) {
                            if(k == i||k == size-i){
                                    System.out.print("**");
                            }else{
                                    System.out.print("  ");
                            }
                    }
                    System.out.println("");
            }
            for (int i = 0; i < size/2; i++) {
                    for (int j = 1; j < size/2-i; j++) {
                            System.out.print(" ");
                    }
                    for (int k = 0; k <= 2*(i+1); k=k+2) {
                            if(k == 0||k == 2*(i+1)){
                                    System.out.print("**");
                            }else{
                                    System.out.print("  ");
                            }
                    }
                    System.out.println("");
            }
    }
	
    
    //交换二个值
    public void swap(Integer a ,Integer b){
    	int temp=a;
    	a=b;
    	b=temp;
    	System.out.println("swap里面a的值为{"+a+"},b的值为{"+b+"}");
    	
    }
    
}


