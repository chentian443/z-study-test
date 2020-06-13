package org.springevent.test.springlife;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ConfigForSpringLifeTest {

	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	public SpringLifeTestBean springLifeTestBean() {
	    return new SpringLifeTestBean();
	  }
}
