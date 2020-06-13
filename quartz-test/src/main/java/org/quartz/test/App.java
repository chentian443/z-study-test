package org.quartz.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

/**
 * Hello world!
 */

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@MapperScan("org.quartz.test.mapper")
public class App 
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
