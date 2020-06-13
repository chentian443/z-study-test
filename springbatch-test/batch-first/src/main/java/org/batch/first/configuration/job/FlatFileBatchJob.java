package org.batch.first.configuration.job;

import org.batch.first.configuration.RepositoryPrimaryConfig;
import org.batch.first.configuration.RepositorySecondaryConfig;
import org.batch.first.configuration.listener.JdbcBatchItemWriterJobListener;
import org.batch.first.configuration.listener.RepositoryItemWriterJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class FlatFileBatchJob {

	/**
	 * 用来注册一个job
	 */
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Step repositoryItemWriterStep;

    @Autowired
    private Step jdbcBatchItemWriterStep;

    /**
     * 实例化一个job
     * @param listener
     * @return
     */
    @Bean
    public Job jobUseFlatFileWithRepositoryItemWriter(RepositoryItemWriterJobListener listener) {
        return jobBuilderFactory
                .get("jobUseFlatFileWithRepositoryItemWriter")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(repositoryItemWriterStep)
                .end()
                .build();
    }

    @Bean
    public Job jobUseFlatFileWithJdbcBatchItemWriter(JdbcBatchItemWriterJobListener listener) {
        return jobBuilderFactory.get("jobUseFlatFileWithJdbcBatchItemWriter")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(jdbcBatchItemWriterStep)
                .end()
                .build();
    }


}
