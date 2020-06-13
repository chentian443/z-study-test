package org.batch.easy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class EasyApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EasyApplication.class, args);
    }

}


