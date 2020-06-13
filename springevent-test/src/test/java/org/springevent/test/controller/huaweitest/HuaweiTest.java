package org.springevent.test.controller.huaweitest;

import java.util.Scanner;

public class HuaweiTest {

	public static int lastWordLength(String str){
        if(str==null){
            return 0;
        }
        String[] strArr = str.split(" ");
        if(strArr.length==0){
            return 0;
        }
        
        return strArr[strArr.length-1].length();
    }
	
	public static int lastWordLength2(String str){
        if(str==null){
            return 0;
        }
        return str.substring(str.lastIndexOf(" ")+1).length();
    }
	
    public static void main(String[] args){
    	/*Scanner input =new Scanner(System.in);
    	String inputStr = input.nextLine();*/
        System.out.println(lastWordLength("asd qwe sadfsadf"));
        System.out.println(lastWordLength2("asd qwe sadfsadf"));

    }
}
