package org.springevent.test.controller.huaweitest;

import java.util.Scanner;

public class Test6 {

	public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        long num = sc.nextLong();
        StringBuilder sb = new StringBuilder();
        for(int i=2;i<=num/2;i++){
        	if(num%i != 0){
        		break;
        	}
            boolean tag=false;
            for(int j=2;j<=i/2;j++){
                if(i%j==0){
                    tag=true;
                    break;
                }
            }    
            if(!tag){
                sb.append(i).append(" ");
            }
        }
        System.out.println(sb);
    }
}
