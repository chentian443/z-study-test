package org.websocket.demo;

/**
 * Created by yangyibo on 16/12/29.
 */
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
    }
}