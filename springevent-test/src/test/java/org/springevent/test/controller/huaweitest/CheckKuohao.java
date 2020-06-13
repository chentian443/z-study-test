package org.springevent.test.controller.huaweitest;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * 给定一个字符串，里边可能包含“()”、“[]”、“{}”三种括号，请编写程序检查该字符串中的括号是否成对出现，且嵌套关系正确。 
 * 输出：
 * 	   true:若括号成对出现且嵌套关系正确，或该字符串中无括号字符； 
 *     false:若未正确使用括号字符。 
 *
 */
public class CheckKuohao {
	
	public static boolean isKuohao(char str){
		return '(' == str || ')'== str || '['== str || ']'== str
				 || '{'== str || '}'== str;
	}
	
	public static boolean isPair(char left, char right){
		return ('('==left && ')'==right) ||
				('['==left && ']'==right) ||
				('{'==left && '}'==right);
	}
	
	public static void main(String[] args) {
		Scanner input =new Scanner(System.in);
        String str = input.nextLine();
        
        ArrayList<Character> al =new ArrayList<>(); 
        
        for (char c : str.toCharArray()) {
        	if(!isKuohao(c)){
        		continue;
        	}
        	if(al.size()==0){
        		al.add(c);
        		continue;
        	}
        	
			char last = al.get(al.size()-1);
			if(isPair(last, c)){ // 成对，消去
				al.remove(al.size()-1);
			}else{
				al.add(c);
			}
		}
        
        System.out.println(al.size() == 0);
	}
	
	
}
