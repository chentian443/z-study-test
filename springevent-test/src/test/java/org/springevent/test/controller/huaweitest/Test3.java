package org.springevent.test.controller.huaweitest;

import java.util.Scanner;
import java.util.TreeSet;

public class Test3 {
	
	public static void main(String[] args) {
		Scanner input =new Scanner(System.in);
        String firstStr = input.nextLine();
        int num = Integer.parseInt(firstStr);
        
        TreeSet<Integer> ts =new TreeSet<>();
        
        for(int i=0; i<num; i++){
        	String str = input.nextLine();
        	Integer number = Integer.parseInt(str);
        	ts.add(number);
        }
        
        for (Integer integer : ts) {
			System.out.println(integer);
		}
        
	}
}
