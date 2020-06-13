package org.springevent.test.controller.statictest;

public class SonClass extends FatherClass {
	
	public static int sonvalue = 10;
	
    static {
        System.out.println("SonClass init!");
    }
    
    public static void count(){
    	System.out.println("SonClass count");
    }
}