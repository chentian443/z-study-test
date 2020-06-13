package org.mapreduce.study;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

public class WordCountMapperTest {

	private Mapper mapper;
    private MapDriver driver;
    
    @Before
    public void init() {   //初始化
        mapper = new WordCountMapper(); //通过Temperature下的Mapper方法对mapper进行初始化
        driver = new MapDriver(mapper); 
    }
    
    @Test  //使用注解添加测试方法，获得入口函数
    public void test() throws IOException {
        String line = "hello hello";
        driver.withInput(new LongWritable(), new Text(line))
            .withOutput(new Text("hello"), new IntWritable(2)) //"03103"代表测试文件编号；22代表输入测试数据中第五位数，代表气温值
            .runTest();
    }

}
