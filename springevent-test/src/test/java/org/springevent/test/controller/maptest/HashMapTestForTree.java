package org.springevent.test.controller.maptest;

import java.util.HashMap;
import java.util.Map;

public class HashMapTestForTree {

	
	public static void main(String[] args) {
		new HashMapTestForTree().test();
	}
	
	/**
	 * 调试树华
	 */
	private void test(){
		
		Map<Mykey, Integer> map = new HashMap<>();
		/*for(int i=1; i<100; i++){
			System.out.println((15 & i) + ":" + (31 & i));
		}*/
		
		
		
		for(int i=1; i<100; i++){
			map.put(new Mykey(i), i);
			System.out.println(i);
		}
		
	}
	
	
	class Mykey{
		
		private int value;
		
		public Mykey(){}
		
		public Mykey(int value){
			this.value = value;
		}
		
		
		@Override
		public int hashCode(){
			if(this.value>64){
				return 0;
			}
			return this.value % 64;
		}
		
		
	}
}
