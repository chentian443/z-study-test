package org.quartz.test.mapper;

import java.util.List;

import org.quartz.test.entity.JobAndTrigger;


public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
