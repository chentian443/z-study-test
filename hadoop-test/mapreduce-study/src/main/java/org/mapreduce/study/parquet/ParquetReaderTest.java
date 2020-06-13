package org.mapreduce.study.parquet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.GroupFactory;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetReader.Builder;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
 

public class ParquetReaderTest {
	
	public static void main(String[] args) {
		String outputPath = "D:/BaiduNetdiskDownload/mapreduce_test/parquetTest/output/part-m-00000.parquet";
		try {
			parquetReaderV2(outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void parquetReaderV2(String inPath) throws Exception{
	    GroupReadSupport readSupport = new GroupReadSupport();
	    Builder<Group> reader= ParquetReader.builder(readSupport, new Path(inPath));
	    ParquetReader<Group> build=reader.build();
	    Group line=null;
	    while((line=build.read())!=null){
	    	String str = line.getString("line", 0);
	    	long offset = line.getLong("offset", 0);
			System.out.println(offset+":"+str);
	    }
	    System.out.println("读取结束");
	  } 

}
