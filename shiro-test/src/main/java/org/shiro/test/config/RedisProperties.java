package org.shiro.test.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "mycache.list")
public class RedisProperties {
	private Map<String, String> namesAndExpires;
}

