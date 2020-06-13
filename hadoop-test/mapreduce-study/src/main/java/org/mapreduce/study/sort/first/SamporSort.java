package org.mapreduce.study.sort.first;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler.RandomSampler;
 
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
 
public class SamporSort {
 
	public static void main(String[] args) throws Exception{
		//设置数据读入、输出路径
		Path inputPath = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\input");
		Path outputPath = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\samporOutput");
		//设置分区文件路径
		Path partitionFile = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\samporParOutput\\par");
		//设置reduce任务数
		int numReduceTasks = 4;
		
		//设置样本数据抽样
		//RandomSampler第一个参数表示会被选中的概率
		//第二个参数表示选取的样本数
		//第三个参数表示最大读取InputSplit数
		RandomSampler<Text, Text> sampler = new InputSampler.RandomSampler<Text, Text>(0.1, 10, 1);
		
		//加载hadoop配置信息
		Configuration conf = new Configuration();
		//设置作业的分区文件路径
		TotalOrderPartitioner.setPartitionFile(conf, partitionFile);
		
		//初始化作业信息
		Job job = Job.getInstance(conf);
		job.setJarByClass(SamporSort.class);
				
		//设置文件数据输入类型（或者称读入方式）
        //KeyValueTextInputFormat类，会将文件的每一行默认按第一个制表符'\t'分割为key/value对（都是Text类型）
		//若制表符不存在，则将整行作为key，value置为null
		job.setInputFormatClass(KeyValueTextInputFormat.class);
        //不需要设置Mapper和Reducer类，使用默认的
		//设置Mapper输出键值对类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
 
		//设置reduce任务个数
		job.setNumReduceTasks(numReduceTasks);
		
		//设置分区类
		job.setPartitionerClass(TotalOrderPartitioner.class);
		
		//设置文件输入/输出路径
		FileInputFormat.addInputPath(job, inputPath);
		FileSystem fs = outputPath.getFileSystem(conf);
		if (fs.exists(outputPath)) {
			//删除输出路径，递归删除子目录文件
			fs.delete(outputPath, true);
		}
		FileOutputFormat.setOutputPath(job, outputPath);
		
		//写入分区文件（需要读取输入文件）
		InputSampler.writePartitionFile(job, sampler);
		
		//提交作业
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
 
}

