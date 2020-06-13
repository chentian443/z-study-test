package org.springevent.test.controller.maptest;

import java.util.LinkedHashMap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全的 LRU淘汰
 * @author chent
 *
 * @param <K>
 * @param <V>
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = -952299094512767664L;
	
	private final int maxCapacity;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();
 
	public LRULinkedHashMap(int maxCapacity) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
		this.maxCapacity = maxCapacity;
	}
	
	/**
	 * 重写removeEldestEntry策略；默认的LinkedHashMap并不会移除旧元素，如果需要移除旧元素，则需要重写removeEldestEntry()方法设定移除策略；
	 */
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > maxCapacity;
	}
 
	@Override
	public V get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}
	//可以根据实际情况，考虑对不同的操作加锁
	@Override
	public V put(K key, V value) {
		try {
            lock.lock();
			return super.put(key, value);
		} finally {
            lock.unlock();
		}
	}
 
}
