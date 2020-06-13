package org.springevent.test.controller.cloneTest;

import java.util.ArrayList;
import java.util.List;

public class Ztest {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		test2();
	}
	
	private static void test2(){
		Father father = new Father();
		father.setFatherName("father");
		father.setFatherAddress("jiangxi");
		Son son = new Son("son", 15);
		father.setSon(son);
		Father father1 = father.fatherClone();
		
		father1.setFatherName("father1");
		son.setSonName("bigson");
		
		System.out.println(father);
		System.out.println(father.hashCode());
		System.out.println(father1);
		System.out.println(father1.hashCode());
		
		
		
	}
}
