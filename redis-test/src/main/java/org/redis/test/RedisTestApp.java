package org.redis.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * redis做缓存，并且session存储于redis
 *
 */
@EnableSwagger2
@EnableCaching //springboot 默认实现currentMapCache缓存
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=1800,redisNamespace="SID")    //允许redis存储spring session
@SpringBootApplication
public class RedisTestApp 
{
    public static void main( String[] args)
    {
    	SpringApplication.run(RedisTestApp.class, args);
    }
}

