package org.elasticsearch.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "org.elasticsearch.test.elast")
public class ElasticSearchApp 
{
    public static void main( String[] args)
    {
    	SpringApplication.run(ElasticSearchApp.class, args);
    }
}
