package org.quartz.test.config.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Administrator
 * @date 2018/08/28
 */
public class AsyncJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	System.out.println("========================立即执行的任务，只执行一次===============================");
        System.out.println("jobName=====:"+jobExecutionContext.getJobDetail().getKey().getName());
        System.out.println("jobGroup=====:"+jobExecutionContext.getJobDetail().getKey().getGroup());
        System.out.println("taskData=====:"+jobExecutionContext.getJobDetail().getJobDataMap().get("asyncData"));
    }
}
