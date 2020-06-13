package org.springevent.test.controller.huaweitest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test5 {

	public static int covert(String content){
        int number=0;
        String [] HighLetter = {"A","B","C","D","E","F"};
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i <= 9;i++){
            map.put(i+"",i);
        }
        for(int j= 10;j<HighLetter.length+10;j++){
            map.put(HighLetter[j-10],j);
        }
        String[]str = new String[content.length()];
        for(int i = 0; i < str.length; i++){
            str[i] = content.substring(i,i+1);
        }
        for(int i = 0; i < str.length; i++){
            number += map.get(str[i])*Math.pow(16,str.length-1-i);
        }
        return number;
    }
	
    public static void main(String... args) {
        Scanner input = new Scanner(System.in);
        String content = input.nextLine();
        if(!content.matches("[0-9a-fA-F]*")){
            System.out.println("输入不匹配");
            System.exit(-1);
        }
        //将全部的小写转化为大写
        content = content.toUpperCase();
        System.out.println(covert(content));
    }
	
}
