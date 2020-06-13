package org.shiro.test.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.shiro.test.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("test")
@Api(value = "测试redis缓存")
public class TestController {

	@Autowired
	private CacheService cacheService;
	
	@GetMapping("/test")
	public String test(){
		return "test";
	}
	
	
	@GetMapping("/putCache")
	@ApiOperation(value = "生成数据并放入缓存")
	public String putCache(){
		cacheService.putCache("list-map-data");
		return "list<map> has been put in cache";
	}
	
	@GetMapping("/getCache")
	@ApiOperation(value = "获取缓存数据")
	public List<Map<String, Object>> getCache(){
		List<Map<String, Object>> list = cacheService.getCache("list-map-data");
		return list;
	}
	
	@GetMapping("/cleanCache")
	@ApiOperation(value = "清除缓存")
	public String cleanCache(){
		cacheService.cleanCache("list-map-data");
		return "cache has been clean";
	}
	
	
	@GetMapping(value="/getSessionId")
	public String getSessionId(HttpServletRequest request){
		
		Object o = request.getSession().getAttribute("springboot");
		if(o == null){
			log.info("there is no key which is springboot in session");
			o = "spring boot 牛逼了!!!有端口"+request.getLocalPort()+"生成";
			request.getSession().setAttribute("springboot", o);
		}else{
			log.info("the key which is springboot in session is from redis");
		}
		
		return "端口=" + request.getLocalPort() +  " sessionId=" + request.getSession().getId() +"<br/>"+o;
	}

	
}
