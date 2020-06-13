package org.springevent.test.controller.maptest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HashMapTest {

	static final int MAXIMUM_CAPACITY = 1 << 30;
	
	static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1; // n = n | n >>> 1
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
	
	/**
	 * >>：带符号右移。正数右移高位补0，负数右移高位补1。比如：
	 * 	4 >> 1，结果是2；-4 >> 1，结果是-2。-2 >> 1，结果是-1。
	 * >>>：无符号右移。无论是正数还是负数，高位通通补0。
	 * 对于正数而言，>>和>>>没区别。
	 * |：或运算；&：与运算
	 */
	
	public static void main(String[] args) {
		System.out.println(3<<3); // 3×2×2×2
		
		System.out.println(102>>2); // 25
		System.out.println(102>>>2); // 25
		System.out.println(-102>>2); // -26
		System.out.println(-10>>>2); // 1073741798
		
		System.out.println(-51/2);
		
		
		System.out.println(tableSizeFor(3));
		
		
		List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
		synchronizedList.add("abc");
		System.out.println(synchronizedList);
	}
}
