package org.mapreduce.study.course;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CourseDriver {
	public static void main(String[] args) throws Exception {
        /**
         * 一些参数的初始化
         */
        String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseInput";
        String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseOutput";
 
        /**
         * 初始化一个Job对象
         */
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
 
        /**
         * 设置jar包所在路径
         */
        job.setJarByClass(CourseDriver.class);
 
        /**
         * 指定mapper类和reducer类 等各种其他业务逻辑组件
         */
        job.setMapperClass(CourseMapper.class);
        job.setReducerClass(CourseReducer.class);
        // 指定maptask的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        // 指定reducetask的输出类型
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

}
