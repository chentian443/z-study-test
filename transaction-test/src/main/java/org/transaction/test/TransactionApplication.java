package org.transaction.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试spring事务
 * 传播性质
 */
@SpringBootApplication
public class TransactionApplication 
{
    public static void main( String[] args)
    {
    	SpringApplication.run(TransactionApplication.class, args);
    }
}
