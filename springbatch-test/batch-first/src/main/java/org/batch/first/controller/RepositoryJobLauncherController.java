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
@RequestMapping("/repos")
public class RepositoryJobLauncherController {

    @Autowired
    private JobLaunchService jobLaunchService;

    @Autowired
    private Job jobWithRepositoryStepWithParams;

    @Autowired
    private Job jobWithRepositoryStep;

    @GetMapping("/param")
    public JobResult launchJobWithRepositoryStepWithParams() {
        return jobLaunchService.launchJob(jobWithRepositoryStepWithParams);
    }

    @GetMapping
    public JobResult launchJobWithRepositoryStep() {
        return jobLaunchService.launchJob(jobWithRepositoryStep);
    }


}
