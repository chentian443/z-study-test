package org.mapreduce.study.course.more;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
 
import java.io.IOException;

/**
 * 求该成绩表每门课程当中出现了相同分数的分数，还有次数，以及该分数的人数(computer 85 3 huangzitao,liujialing,huangxiaoming)
 * @author chent
 */
public class CourseScoreMR_Basic_02 {
 
    public static void main(String[] args) throws Exception {
        /**
         * 一些参数的初始化
         */
    	String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseInput";
        String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseOutput2";
 
        /**
         * 初始化一个Job对象
         */
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
 
        /**
         * 设置jar包所在路径
         */
        job.setJarByClass(CourseScoreMR_Basic_02.class);
 
        /**
         * 指定mapper类和reducer类 等各种其他业务逻辑组件
         */
        job.setMapperClass(Mapper_CS.class);
        job.setReducerClass(Reducer_CS.class);
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
 
    /**
     * Mapper组件：
     * <p>
     * 输入的key:
     * 输入的value: computer,huangxiaoming,85
     * <p>
     * 输出的key: course +"\t"+ score
     * 输入的value: name
     */
    private static class Mapper_CS extends Mapper<LongWritable, Text, Text, Text> {
 
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
 
            String[] splits = value.toString().split(",");
            String course = splits[0];
            String score = splits[2];
            String name = splits[1];
            Text keyOut = new Text(course +"\t"+ score);
            Text valueOut = new Text(name);
 
            context.write(keyOut, valueOut);
        }
    }
 
    /**
     * Reducer组件：
     * <p>
     * 输入的key: course +"\t"+ score
     * 输入的values: name
     * <p>
     * 输出的key:  course + "\t" + score
     * 输入的value:   number + "\t" + names
     */
    private static class Reducer_CS extends Reducer<Text, Text, Text, Text> {
 
        Text valueOut = new Text();
 
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
 
            StringBuilder sb = new StringBuilder();
            int number = 0;
            for(Text t: values){
                sb.append(t.toString()).append(",");
                number++;
            }
 
            if(number > 1){
                String names = sb.toString().substring(0, sb.toString().length() - 1);// 去掉最后一个逗号
                valueOut.set(number + "\t" + names);
                context.write(key, valueOut);
            }
        }
    }
}

