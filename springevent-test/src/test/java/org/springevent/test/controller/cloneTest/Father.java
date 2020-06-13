package org.springevent.test.controller.cloneTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class Father implements Cloneable{

	private String fatherName;
	private String fatherAddress;
	private Son son;
	
	
	public Father fatherClone(){
		
		
		
		List<String> list = new ArrayList<>();
		List<String> linkedList = new LinkedList<>();
		List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
		List<String> synchronizedList = Collections.synchronizedList(null);
		
		Map<String, Object> hashmap = new HashMap<>();
		Map<String, Object> treemap = new TreeMap<>();
		ConcurrentHashMap<String, String> cmap = new ConcurrentHashMap<>();
		ConcurrentSkipListMap<String, String> cslm = new ConcurrentSkipListMap<>();
		
		
		Set<String> set = new HashSet<>();
		Set<String> linkedHashSet = new LinkedHashSet<>();
		Set<String> treeSet = new TreeSet<>();
		Set<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();// 通过CopyOnWriteArrayList实现
		Set<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();
		
		Queue<String> priorityQueue = new PriorityQueue<>();
		Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(100);
		
		AtomicInteger ai = new AtomicInteger();
		
		Father father = null;
		try {
			father = (Father) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return father;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherAddress() {
		return fatherAddress;
	}

	public void setFatherAddress(String fatherAddress) {
		this.fatherAddress = fatherAddress;
	}

	public Son getSon() {
		return son;
	}

	public void setSon(Son son) {
		this.son = son;
	}
	
	
	
	@Override
	public String toString() {
		return "Father [fatherName=" + fatherName + ", fatherAddress=" + fatherAddress + ", son=" + son + "]";
	}

	
}
