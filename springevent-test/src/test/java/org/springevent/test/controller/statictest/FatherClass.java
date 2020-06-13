package org.springevent.test.controller.statictest;

public class FatherClass {

	public static int value = 100;
	
	static {
        System.out.println("FatherClass init!");
    }
	
	public static void fatherCount(){
		System.out.println("fatherClass count");
	}
    
}
