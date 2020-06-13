package org.mapreduce.study.sort.first;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class KeyPartitioner extends Partitioner<IntWritable, IntWritable>{
	
	@Override
	public int getPartition(IntWritable key, IntWritable value, int numReduceTasks) {
 
        //注：此处三分区的实现不能用 对numReduceTasks取余操作，以免key在取余后循环顺序
		//获取key的第一个排序字段
		int key_1 = Integer.parseInt(key.toString());
		return key_1 % 3;
		
	}
}
