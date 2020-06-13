package org.batch.first.configuration.job;

import org.batch.first.configuration.listener.CommonJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    /**
     * 以下会注入两个相同类型的step，此时会选用byname方式，即bean的name与该属性的name一致就可以自动注入。
     * autowired默认使用bytype，如果容器中有多个相同type的bean，则使用byname。
     */
    @Autowired
    private Step stepWithRepositoryReaderAndWriter;

    @Autowired
    private Step stepWithRepository;

    @Bean
    public Job jobWithRepositoryStepWithParams(@Autowired CommonJobListener commonJobListener) {
        return jobBuilderFactory.get("jobWithRepositoryStepWithParams")
                .incrementer(new RunIdIncrementer())
                .listener(commonJobListener)
                .flow(stepWithRepositoryReaderAndWriter)
                .end()
                .build();
    }

    @Bean
    public Job jobWithRepositoryStep(@Autowired CommonJobListener commonJobListener) {
        return jobBuilderFactory.get("jobWithRepositoryStep")
                .incrementer(new RunIdIncrementer())
                .listener(commonJobListener)
                .flow(stepWithRepository)
                .end()
                .build();
    }
}
