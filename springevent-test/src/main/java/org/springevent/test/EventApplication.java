package org.springevent.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAsync
public class EventApplication 
{
    public static void main( String[] args)
    {
    	SpringApplication.run(EventApplication.class, args);
    }
}
