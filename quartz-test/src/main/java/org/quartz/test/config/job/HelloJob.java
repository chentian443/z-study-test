package org.quartz.test.config.job;

import java.util.Date;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.hibernate.validator.constraints.SafeHtml;
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  

public class HelloJob implements BaseJob {  
  
    
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        System.out.println("Hello Job执行时间: " + new Date());
    }  
}  
