package org.batch.first.configuration.step;

import org.batch.first.configuration.processor.UserPeopleItemProcessor;
import org.batch.first.entity.read.User;
import org.batch.first.entity.write.People;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author clyde lou
 */
@Configuration
public class JpaPagingStep {
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("jpaPagingItemReader")
    private ItemReader jpaPagingItemReader;

    @Autowired
    private UserPeopleItemProcessor userPeopleItemProcessor;

    @Autowired
    private JdbcBatchItemWriter jdbcBatchItemWriter;
    @Autowired
    private RepositoryItemWriter repositoryItemWriter;

    @Bean
    public Step stepInJpaPagingWithJdbcBatchItemWriter() {
        return stepBuilderFactory
                .get("stepInJpaPagingWithJdbcBatchItemWriter")
                .<User, People>chunk(10)
                .reader(jpaPagingItemReader)
                .processor(userPeopleItemProcessor)
                .writer(jdbcBatchItemWriter)
                .build();
    }

    @Bean
    public Step stepInJpaPagingWithRepositoryItemWriter() {
        return stepBuilderFactory
                .get("stepInJpaPagingWithRepositoryItemWriter")
                .<User, People>chunk(10)
                .reader(jpaPagingItemReader)
                .processor(userPeopleItemProcessor)
                .writer(repositoryItemWriter)
                .build();
    }
}
