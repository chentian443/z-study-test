package org.springevent.test.controller.huaweitest;

import java.util.Scanner;
public class Test2 {
    public static int countWord(String string, char str){
        if(string==null){
            return 0;
        }
        int count = 0;
        for (char c : string.toLowerCase().toCharArray()) {
			if(c == str){
                count ++;
            }
		}
        return count;
    }
    public static void main(String[] args){
        Scanner input =new Scanner(System.in);
        String string = input.nextLine();
        char str = input.nextLine().charAt(0);
        System.out.println(countWord(string, str));
    }
}