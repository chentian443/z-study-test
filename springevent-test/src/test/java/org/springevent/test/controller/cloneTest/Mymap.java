package org.springevent.test.controller.cloneTest;

public class Mymap {
	
	// 初始容量16
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
	// 最大容量 2的30次方
	static final int MAXIMUM_CAPACITY = 1 << 30;
	// 加载因子
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	// 链表上元素个数大于等于8时，转为红黑树
	static final int TREEIFY_THRESHOLD = 8;
	// 链表上元素个数小于等于6时，转为链表
	static final int UNTREEIFY_THRESHOLD = 6;
	// 当桶的个数达到64的时候才进行树化
	static final int MIN_TREEIFY_CAPACITY = 64;
}
