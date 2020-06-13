package org.springevent.test.controller;

import org.springevent.test.config.MyApplicationEvent;
import org.springevent.test.resttemp.RestTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
public class EnevtTestController {
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	private RestTempService RestTempService;
	
	/**
	 * localhost:8080/event?eventName=tian&eventNum=4
	 * @param eventName
	 * @param eventNum
	 * @return
	 */
	@GetMapping("/event")
	@ResponseBody
	public String testEvent(String eventName,Integer eventNum){
		if(eventNum>0 && eventNum<35){
			for(int i=0;i<eventNum;i++){
				applicationEventPublisher.publishEvent(new MyApplicationEvent(eventName));
			}
			return "success";
		}
		return "eventNum must in (0,35)";
	}
	
	@GetMapping("/go")
	public String go(){
		return "redirect:http://172.16.10.130:11180/web/retrieval/home";
	}
	
	@ResponseBody
	@GetMapping("/getSessionJson")
	public String getSessionJson(){
		String body = RestTempService.getSessionJson();
		return body;
	}
}
