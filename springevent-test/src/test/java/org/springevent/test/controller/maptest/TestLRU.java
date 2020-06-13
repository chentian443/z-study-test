package org.springevent.test.controller.maptest;

import java.util.Map;

public class TestLRU {

	
	public static void main(String[] args) {
		Map<String,String> map = new LRULinkedHashMap<>(5);
		
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6"); // 把1挤掉
		
		System.out.println(map); // {2=2, 3=3, 4=4, 5=5, 6=6}
		
		System.out.println(map.get("2"));// 因为accessOrder为true，所以2移到了最后
		
		map.put("7", "7");// 挤掉的是3
		System.out.println(map); // {4=4, 5=5, 6=6, 2=2, 7=7}
		map.get("4");// 4移到了最后
		System.out.println(map);
	}
}
