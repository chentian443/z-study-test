package org.batch.easy.controller;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.batch.easy.entity.JobResult;
import org.batch.easy.service.JobLaunchService;

@RestController
@Slf4j
public class TestController {

	@Autowired
	private JobLaunchService JobLaunchService;
	
	@Autowired
	@Qualifier("helloWorldJob")
	private Job helloWorldJob;
	
	@Autowired
	@Qualifier("taskletDemoJob")
	private Job taskletDemoJob;
	
	@GetMapping("/helloWorld")
	public JobResult helloWorld(){
		log.info("get in test helloWorldJob");
		JobResult jobResult=JobLaunchService.launchJob(helloWorldJob);
		return jobResult;
	}
	
	@GetMapping("/taskletDemo")
	public JobResult test(){
		log.info("get in test taskletDemoJob");
		JobResult jobResult=JobLaunchService.launchJob(taskletDemoJob);
		return jobResult;
	}
	
	@GetMapping("/testLog")
	public String testLog(){
		log.debug("this is test for info");
		log.info("this is test for info");
		log.warn("this is test for warn");
		log.error("this is test for error");
		return "testLog";
	}
}
