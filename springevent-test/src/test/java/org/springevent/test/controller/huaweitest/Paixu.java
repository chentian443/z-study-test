package org.springevent.test.controller.huaweitest;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * 去除重复字符并排序 
 * 输入： 字符串 
 * 输出： 去除重复字符并排序的字符串
 * 样例输入： aabcdefff 样例输出： abcdef
 *
 */
public class Paixu {

	public static void main(String[] args) {
		
		Scanner input =new Scanner(System.in);
        String str = input.nextLine();
        TreeSet<Character> ts =new TreeSet<>(); 
        for (char c : str.toCharArray()) {
			ts.add(c);
		}
        StringBuilder sb = new StringBuilder();
        for (Character character : ts) {
			sb.append(character);
		}
        System.out.println(sb);
	}
}
