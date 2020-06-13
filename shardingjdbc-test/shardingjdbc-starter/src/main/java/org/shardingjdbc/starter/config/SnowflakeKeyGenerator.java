package org.shardingjdbc.starter.config;

import org.shardingjdbc.starter.util.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.shardingsphere.core.keygen.KeyGenerator;

@Component
public class SnowflakeKeyGenerator implements KeyGenerator,InitializingBean{
	@Value("${snow.work.id:0}")
    private Long workId;
 
    @Value("${snow.datacenter.id:0}")
    private Long datacenterId;
    
    private SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator;

	@Override
	public Number generateKey() {
		return this.snowflakeShardingKeyGenerator.generateKey();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.snowflakeShardingKeyGenerator=new SnowflakeShardingKeyGenerator(workId, datacenterId);
	}
    
}
