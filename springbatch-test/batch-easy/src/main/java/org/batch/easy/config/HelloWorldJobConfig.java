package org.batch.easy.config;

import org.batch.easy.entity.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HelloWorldJobConfig {
	
	/**
	 * job
	 * job中指定step，可以指定多个
	 * @param jobBuilders
	 * @param stepBuilders
	 * @return
	 */
	@Bean("helloWorldJob")
	public Job helloWorldJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders) {
		return jobBuilders.get("helloWorldJob").
				start(helloWorldStep(stepBuilders)).
				//next(step).
				build();
	}
	
	/**
	 * step
	 * step中指定reader、processor、writer。
	 * chunk：为了减少一次一次写的io浪费，指定多次完成后再写一次
	 * @param stepBuilders
	 * @return
	 */
	@Bean
	public Step helloWorldStep(StepBuilderFactory stepBuilders) {
		return stepBuilders.get("helloWorldStep").<Person, String>chunk(10).
				reader(reader()).
				processor(processor()).
				writer(writer()).
				build();
	}

	/**
	 * 读操作
	 * 
	 * @return
	 */
	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
				.resource(new ClassPathResource("csv/person.csv")).delimited()
				.names(new String[] { "firstName", "lastName" }).targetType(Person.class).build();
	}

	/**
	 * 处理操作
	 * 
	 * @return
	 */
	@Bean
	public ItemProcessor<Person, String> processor() {
		return (person) -> {
			// 模拟处理
			String greeting = "Hello " + person.getFirstName() + " " + person.getLastName() + "!";
			log.info("converting '{}' into '{}'", person, greeting);
			return greeting;
		};
	}

	/**
	 * 写操作
	 * 
	 * @return
	 */
	@Bean
	public FlatFileItemWriter<String> writer() {
		return new FlatFileItemWriterBuilder<String>().name("greetingItemWriter")
				.resource(new FileSystemResource("target/test-outputs/greetings.txt"))
				.lineAggregator(new PassThroughLineAggregator<>()).build();
	}
}
