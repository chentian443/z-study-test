package org.batch.first.configuration.step;

import org.batch.first.configuration.processor.UserPeopleItemProcessor;
import org.batch.first.entity.read.User;
import org.batch.first.entity.write.People;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author clyde lou
 */
@Configuration
public class RepositoryStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RepositoryItemReader repositoryItemReaderWithParams;
    @Autowired
    private RepositoryItemReader repositoryItemReader;

    @Autowired
    private UserPeopleItemProcessor userPeopleItemProcessor;

    @Autowired
    private RepositoryItemWriter repositoryItemWriter;

    /**
     * 默认情况下bean的名称和方法名称相同，所以多个相同类型的bean，autowired注入时会以byname的方式
     * @return
     */
    @Bean
    public Step stepWithRepositoryReaderAndWriter() {
        return stepBuilderFactory.get("stepWithRepositoryReaderAndWriter")
                .<User, People>chunk(10)
                .reader(repositoryItemReaderWithParams)
                .processor(userPeopleItemProcessor)
                .writer(repositoryItemWriter)
                .build();
    }

    @Bean
    public Step stepWithRepository() {
        return stepBuilderFactory.get("stepWithRepository")
                .<User, People>chunk(10)
                .reader(repositoryItemReader)
                .processor(userPeopleItemProcessor)
                .writer(repositoryItemWriter)
                .build();
    }
}
