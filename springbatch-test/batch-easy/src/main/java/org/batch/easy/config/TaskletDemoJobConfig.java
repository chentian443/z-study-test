package org.batch.easy.config;

import org.batch.easy.entity.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskletDemoJobConfig{
	
	@Bean("taskletDemoJob")
	public Job taskletDemoJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders) {
		return jobBuilders.get("taskletDemoJob").
				start(taskletDemoStep1(stepBuilders)).
				next(taskletDemoStep2(stepBuilders)).
				build();
	}
	
	@Bean
	public Step taskletDemoStep1(StepBuilderFactory stepBuilders) {
		return stepBuilders.get("taskletDemoStep1").
				tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("step1-->taskletDemoStep1....");
		                return RepeatStatus.FINISHED;
					}
				}).
				build();
	}
	
	@Bean
	public Step taskletDemoStep2(StepBuilderFactory stepBuilders) {
		return stepBuilders.get("taskletDemoStep2").
				tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("step2-->taskletDemoStep2....");
		                return RepeatStatus.FINISHED;
					}
				}).
				build();
	}
}
