package org.quartz.test.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ConfigurationProperties(prefix = "quartz.scheduler-factory-bean")
public class QuartzProperties {
	
	private Properties quartzProperties;
}
