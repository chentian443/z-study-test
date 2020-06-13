package org.springevent.test.controller.huaweitest;

import java.util.Scanner;

/**
 * 按要求分解字符串，输入两个数M，N；M代表输入的M串字符串，N代表输出的每串字符串的位数，不够补0。
 * 例如：输入2,8， “abc” ，“123456789”，则输出为“abc00000”,“12345678“,”90000000”
 *
 */
public class BuqiString {
	
	public static void main(String[] args) {
		Scanner input =new Scanner(System.in);
		String first = input.nextLine();
		String second = input.nextLine();
		
		print8Str(first);
		print8Str(second);
		
		input.close();
	}
	
	private static void print8Str(String str){
		if(str==null || str.equals("")){
			return;
		}
		char[] strArr = str.toCharArray();
		StringBuilder sb= new StringBuilder();
		for(int j=0; j<strArr.length; j++){
    		sb.append(strArr[j]);
    		if(sb.length()==8){
    			System.out.println(sb);
    			sb = new StringBuilder();
    		}
    	}
		if(sb.length()>0){
    		int gap = 8 - sb.length();
    		for(int k=0; k<gap; k++){
    			sb.append(0);
    		}
    		System.out.println(sb);
    	}
	}
}
