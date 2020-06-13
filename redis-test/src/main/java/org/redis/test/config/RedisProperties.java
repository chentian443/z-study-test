package org.redis.test.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "redis-cache")
public class RedisProperties {
	private int defaultExpireTime;
	private Map<String, String> namesAndExpires;
}

