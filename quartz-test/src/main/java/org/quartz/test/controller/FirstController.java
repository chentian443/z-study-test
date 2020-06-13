package org.quartz.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {
	
	@RequestMapping("first/v1")
	public String first(){
		return "JobManager";
	}
	
	@ResponseBody
	@RequestMapping("first/exit")
	public void exit(){
		/** 
		 * 强行关闭jvm； 线程分为守护线程与非守护线程，其中守护线程为jvm自己使用的线程，如垃圾回收等；
		 * 而Java程序创建的线程为非守护线程；当然Java创建的线程也可以指定为守护线程；
		 * 一般地，非守护线程结束后，相应的jvm实例也会关闭；
		 */
		System.exit(0);
	}
	
	
}
