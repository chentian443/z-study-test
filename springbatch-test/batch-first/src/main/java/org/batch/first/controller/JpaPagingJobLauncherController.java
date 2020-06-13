package org.batch.first.controller;

import org.batch.first.entity.JobResult;
import org.batch.first.service.JobLaunchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clyde lou
 */

@RestController
@Slf4j
@RequestMapping("/jpa")
public class JpaPagingJobLauncherController {

    @Autowired
    private JobLaunchService jobLaunchService;

    @Autowired
    private Job jobInJpaPagingWithJdbcBatchItemWriter;

    @Autowired
    private Job jobInJpaPagingWithRepositoryItemWriter;

    @GetMapping("/jdbc")
    public JobResult launchJobInJpaPagingWithJdbcBatchItemWriter() {
        return jobLaunchService.launchJob(jobInJpaPagingWithJdbcBatchItemWriter);
    }

    @GetMapping("/repos")
    public JobResult launchJobInJpaPagingWithRepositoryItemWriter() {
        return jobLaunchService.launchJob(jobInJpaPagingWithRepositoryItemWriter);
    }


}
