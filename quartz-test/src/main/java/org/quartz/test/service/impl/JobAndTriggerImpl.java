package org.quartz.test.service.impl;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import org.quartz.test.config.job.AsyncJob;
import org.quartz.test.config.job.CronJob;
import org.quartz.test.entity.JobAndTrigger;
import org.quartz.test.mapper.JobAndTriggerMapper;
import org.quartz.test.service.IJobAndTriggerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class JobAndTriggerImpl implements IJobAndTriggerService{

	@Autowired
	private JobAndTriggerMapper jobAndTriggerMapper;
	
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
		PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
		return page;
	}
	
	
	@Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 创建一个定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void addCronJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            } else {
                //构建job信息
                jobDetail = JobBuilder.newJob(CronJob.class).withIdentity(jobName, jobGroup).build();
                //用JopDataMap来传递数据
                jobDetail.getJobDataMap().put("taskData", "hzb-cron-001");

                //表达式调度构建器(即任务执行的时间,每5秒执行一次)
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");

                //按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAsyncJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            }
            else {
                //构建job信息,在用JobBuilder创建JobDetail的时候，有一个storeDurably()方法，可以在没有触发器指向任务的时候，将任务保存在队列中了。然后就能手动触发了
                jobDetail = JobBuilder.newJob(AsyncJob.class).withIdentity(jobName, jobGroup).storeDurably().build();
                jobDetail.getJobDataMap().put("asyncData","this is a async task");
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger") //定义name/group
                        .startNow()//一旦加入scheduler，立即生效
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule())//使用SimpleTrigger
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");

            scheduler.pauseTrigger(triggerKey);
            System.out.println("=========================pause job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
            scheduler.resumeTrigger(triggerKey);
            System.out.println("=========================resume job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
            scheduler.deleteJob(jobKey);
            System.out.println("=========================delete job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
}