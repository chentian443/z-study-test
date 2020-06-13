package org.shiro.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CacheService {

	@CachePut(value="mapList", key="'my:' + #redisKey.toString()")
	public List<Map<String, Object>> putCache(String redisKey){
		log.info("(putCache:) generate list-map-data ....");
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			Map<String, Object> map = new HashMap<>();
			map.put("key"+i, "value"+i);
			list.add(map);
		}
		list.add(null);
		return list;
	}
	
	@Cacheable(value="mapList", key="'my:' + #redisKey.toString()")
	public List<Map<String, Object>> getCache(String redisKey){
		log.info("(getCache:) generate list-map-data ....");
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			Map<String, Object> map = new HashMap<>();
			map.put("key"+i, "value"+i);
			list.add(map);
		}
		return list;
	}
	
	/**
	 *  allEntries=true 表示mapList缓存全部删除；
	 *  beforeInvocation=true 方法执行之前清空，默认为false
	 *  condition="#redisKey.length()>2"  条件，只删除key长度大于2的； 这个属性Cacheable与CachePut也有
	 */
	@CacheEvict(value="mapList", key="'my:' + #redisKey.toString()")
	public void cleanCache(String redisKey){
		log.info("(cleanCache:) clean cache ....");
	}
	
}
