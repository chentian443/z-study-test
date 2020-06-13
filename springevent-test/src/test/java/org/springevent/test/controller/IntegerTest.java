package org.springevent.test.controller;

public class IntegerTest {

	public static void main(String[] args) {
		Integer a = 100;
		Integer b = 100;
		Integer c = 200;
		Integer d = 200;
		
		
		// Integer的值比较要用equals，或者转换为int；大于128之后就不相等了
		System.out.println(a == b); // true
		System.out.println(c == d); // false
		System.out.println(c.intValue() == d); // true
		System.out.println(c.equals(d)); // true
		
		
	}
	
	private void test(){
		byte a = 127;
		byte b = 127;
		// java中对于byte、short、char的数学运算，会将其类型上升为int
		//b = a + b; // 报编译错误:cannot convert from int to byte
		b += a;
		
		short s1 = 1;
		//s1 = s1 + 1; // 报编译错误:cannot convert from int to short
		s1 += 1;
	}
}
