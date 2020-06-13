package org.mapreduce.study.sort.first;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
 
import java.io.IOException;

/**
 * @author chent
 */
public class SingleReducerSort {
 
    public static void main(String[] args) throws Exception {
    	String inputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\input";
        String outputPath = "D:\\BaiduNetdiskDownload\\mapreduce_test\\sortTest\\singleOutput";
 
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
 
        job.setJarByClass(SingleReducerSort.class);
 
        job.setMapperClass(Mapper_CS.class);
        job.setReducerClass(Reducer_CS.class);
        // 指定maptask的输出类型
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 指定reducetask的输出类型
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
 
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
     * map处理的结果会自动按照key的ascii编码排序，
     * 但是 相同的key值下，value并没有去做排序。
     * 
     */
    private static class Mapper_CS extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] infos = value.toString().split("\t");
            context.write(new IntWritable(new Integer(infos[0])), new IntWritable(new Integer(infos[1])));
        }
    }
 
    /**
     * 对于单reducer来说，map已经按照key做好了排序，reduce处理的结果则自然是排好序的；
     * 但要注意：对于多分区来说（即多个reducer），每个reducer是排好的顺序，但是综合到一起却不是有序的
     */
    private static class Reducer_CS extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        	for (IntWritable value : values) {
    			context.write(key, value);
    		}
        }
    }
}

