package org.mapreduce.study.sort.first;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author chent
 */
public class SingleReducerCompraeSort {
 
	public static void main(String[] args) throws Exception {
    	String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\input";
        String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\compareOutput";
 
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
 
        job.setJarByClass(SingleReducerCompraeSort.class);
 
        job.setMapperClass(Mapper_CS.class);
        job.setReducerClass(Reducer_CS.class);
        // 指定maptask的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        // 指定reducetask的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        
        //设置排序规则（倒序），默认正序（不需要重新实现排序规则）
        job.setSortComparatorClass(SortComparator.class);
 
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
	
    /**
     * map处理的结果会自动按照key的ascii编码排序，可以自定义排序规则
     */
    private static class Mapper_CS extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }
    
    private static class Reducer_CS extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        	context.write(key, NullWritable.get());
        }
    }
    
}


