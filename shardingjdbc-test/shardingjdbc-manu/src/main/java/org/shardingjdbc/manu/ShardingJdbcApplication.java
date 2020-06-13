package org.shardingjdbc.manu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "org.shardingjdbc.manu.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class ShardingJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingJdbcApplication.class, args);
	}
}
