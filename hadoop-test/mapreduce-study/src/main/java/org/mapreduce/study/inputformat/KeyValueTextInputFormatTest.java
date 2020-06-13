package org.mapreduce.study.inputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KeyValueTextInputFormatTest {
	public static void main(String[] args) throws Exception {
    	String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\formatTest\\input";
    	String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\formatTest\\output";
        Configuration conf = new Configuration();
        // 指定读取数据时key与value的分隔符
        conf.setStrings(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, ",");
        Job job = Job.getInstance(conf);
        // 指定输入格式解析类
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        
        job.setJarByClass(KeyValueTextInputFormatTest.class);
        job.setMapperClass(Mapper_CS.class);
        
        // 指定maptask的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
 
        /**
         * 指定该mapreduce程序数据的输入和输出路径
         */
        Path input = new Path(inputPath);
        Path output = new Path(outputPath);
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, output);
        /**
         * 最后提交任务
         */
        boolean waitForCompletion = job.waitForCompletion(true);
        System.exit(waitForCompletion ? 0 : 1);
    }
 
    private static class Mapper_CS extends Mapper<Text, Text, Text, Text> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            System.out.println(key+":"+value);
            context.write(key, value);
        }
    }
 
    
    
}
