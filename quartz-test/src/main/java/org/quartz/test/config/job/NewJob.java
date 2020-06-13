package org.quartz.test.config.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;  
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;
  
@Slf4j
public class NewJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		// 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
        log.info("------springbootquartzonejob执行"+jobDataMap.get("name").toString()+"###############"+context.getTrigger());
	}  
  
  
}  