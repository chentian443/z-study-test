package org.springevent.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springevent.test.exception.ConsumerException;
import org.springevent.test.exception.NotRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@Slf4j
public class ExceptionTestController {

	@GetMapping("/consumerException")
	public String consumerException(){
		log.info("get in exception...");
		if(true){
			throw new ConsumerException("抛出ConsumerException异常");
		}
		return "consumerException";
	}
	
	@GetMapping("/runtime")
	public String runtime(){
		log.info("get in runtime...");
		if(true){
			throw new RuntimeException("抛出runtimeException异常");
		}
		return "exception";
	}
	
	@GetMapping("/notruntime")
	public String notruntime(){
		log.info("get in notruntime...");
		try {
			throw new NotRuntimeException("抛出NotRuntimeException异常");
		} catch (NotRuntimeException e) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND, "Foo Not Found", e);
		}
	}
	
	@ExceptionHandler({ ConsumerException.class})
    public void handleException(HttpServletRequest request, HttpServletResponse response) {
        log.warn("get in ExceptionTestController handleException");
        try {
			response.sendError(501, "woqu");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
