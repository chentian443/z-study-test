package org.mapreduce.study.course.more;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 获取每门课程的前两名（需要设置分组组件），并按课程分开存储结果
 * @author chent
 *
 */
public class CourseScoreMR_Pro_03 {
 
    public static void main(String[] args) throws Exception {
    	String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseMore\\courseMoreInput";
        String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\courseMore\\courseMoreOutput2";
 
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
 
        job.setJarByClass(CourseScoreMR_Pro_03.class);
 
        job.setMapperClass(Mapper_CS.class);
        job.setReducerClass(Reducer_CS.class);
        // 指定maptask的输出类型
        job.setMapOutputKeyClass(CourseScore.class);
        job.setMapOutputValueClass(NullWritable.class);
        // 指定reducetask的输出类型
        job.setOutputKeyClass(CourseScore.class);
        job.setOutputValueClass(NullWritable.class);
        // 指定分组组件
        job.setGroupingComparatorClass(CourseScoreGroupComparator.class);
 
        /**
         * 设置reduceTask数量和分区器
         */
        job.setNumReduceTasks(4);
        job.setPartitionerClass(CoursePartitioner.class);
        
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
     * 输入的value: computer,xuzheng,54,52,86,91,42
     * <p>
     * 输出的key: CourseScore
     * 输入的value: NullWritable
     */
    private static class Mapper_CS extends Mapper<LongWritable, Text, CourseScore, NullWritable> {
 
        CourseScore keyOut = new CourseScore();
 
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
 
            String[] splits = value.toString().split(",");
            String course = splits[0];
            String name = splits[1];
 
            int sum = 0;
            int num = 0;
            for(int i=2; i<splits.length; i++){
                sum += Integer.valueOf(splits[i]);
                num ++;
            }
            double avgScore = Math.round(sum * 1D / num * 10) / 10D;
 
            keyOut.setCourse(course);
            keyOut.setName(name);
            keyOut.setScore(avgScore);
 
            context.write(keyOut, NullWritable.get());
        }
    }
 
    /**
     * Reducer组件：
     * <p>
     * 输入的key: CourseScore
     * 输入的values: NullWritable
     * <p>
     * 输出的key: CourseScore
     * 输入的value: NullWritable
     */
    private static class Reducer_CS extends Reducer<CourseScore, NullWritable, CourseScore, NullWritable> {
 
        // 成绩最高的两个人的信息
        int topN = 2;
 
        @Override
        protected void reduce(CourseScore key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
 
            int number = 0;
            for(NullWritable nvl: values){
                context.write(key, nvl);
                number ++;
                if(number == topN){
                    break;
                }
            }
        }
    }
 
    /**
     * 自定义分组组件
     */
    public static class CourseScoreGroupComparator extends WritableComparator{
 
        public CourseScoreGroupComparator(){
            super(CourseScore.class, true);
        }
 
        @Override
        public int compare(WritableComparable a, WritableComparable b) {
 
            CourseScore cs1 = (CourseScore)a;
            CourseScore cs2 = (CourseScore)b;
 
            int result = cs1.getCourse().compareTo(cs2.getCourse());
            return result;
        }
    }
    
    /**
     * 自定义分区组件，getParttion方法用于决定当前key存储于哪一个partition，apache提供了HashPartition实现类
     */
    public static class CoursePartitioner extends Partitioner<CourseScore, NullWritable>{
    	
    	/**
    	 * numPartitions为所有分区总数，
    	 */
        @Override
        public int getPartition(CourseScore courseScore, NullWritable nullWritable, int numPartitions) {
 
        	System.out.println("this is the numPartitions:" + numPartitions);
        	
            String course = courseScore.getCourse();
            if(course.equals("computer")){
                return 0;
            }else if(course.equals("english")){
                return 1;
            }else if(course.equals("algorithm")){
                return 2;
            }else{
                return 3;
            }
        }
    }
}

