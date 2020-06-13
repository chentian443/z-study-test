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
@RequestMapping("/flat")
public class FlatFileJobLauncherController {

    @Autowired
    private JobLaunchService jobLaunchService;

    @Autowired
    private Job jobUseFlatFileWithRepositoryItemWriter;

    @Autowired
    private Job jobUseFlatFileWithJdbcBatchItemWriter;

    @GetMapping("/repos")
    public JobResult launchJobUseFlatFileWithRepositoryItemWriter() {
        return jobLaunchService.launchJob(jobUseFlatFileWithRepositoryItemWriter);
    }

    @GetMapping("/jdbc")
    public JobResult launchJobUseFlatFileWithJdbcBatchItemWriter() {
        return jobLaunchService.launchJob(jobUseFlatFileWithJdbcBatchItemWriter);
    }

}
