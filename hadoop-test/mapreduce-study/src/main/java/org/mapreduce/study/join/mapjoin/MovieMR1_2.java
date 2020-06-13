package org.mapreduce.study.join.mapjoin;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MovieMR1_2 {

    public static void main(String[] args) throws Exception {
        if(args.length < 2) {
            args = new String[2];
            args[0] = "/movie/output/";
            args[1] = "/movie/output_last/";
        }
        
        
        Configuration conf1 = new Configuration();
        conf1.set("fs.defaultFS", "hdfs://hadoop1:9000/");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        FileSystem fs1 = FileSystem.get(conf1);
        
        
        Job job = Job.getInstance(conf1);
        
        job.setJarByClass(MovieMR1_2.class);
        
        job.setMapperClass(MoviesMapJoinRatingsMapper2.class);
        job.setReducerClass(MovieMR1Reducer2.class);

        
        job.setMapOutputKeyClass(MovieRating.class);
        job.setMapOutputValueClass(NullWritable.class);
        
        job.setOutputKeyClass(MovieRating.class);
        job.setOutputValueClass(NullWritable.class);
        
        
        Path inputPath1 = new Path(args[0]);
        Path outputPath1 = new Path(args[1]);
        if(fs1.exists(outputPath1)) {
            fs1.delete(outputPath1, true);
        }
        //对第一步的输出结果进行降序排序
        FileInputFormat.setInputPaths(job, inputPath1);
        FileOutputFormat.setOutputPath(job, outputPath1);
        
        boolean isDone = job.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
        

    }
    
    //注意输出类型为自定义对象MovieRating，MovieRating按照降序排序
    public static class MoviesMapJoinRatingsMapper2 extends Mapper<LongWritable, Text, MovieRating, NullWritable>{
        
        MovieRating outKey = new MovieRating();
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            //'Night Mother (1986)         70
            String[] split = value.toString().split("\t");
            
            outKey.setCount(Integer.parseInt(split[1]));;
            outKey.setMovieName(split[0]);
            
            context.write(outKey, NullWritable.get());
                        
        }
                
    }
    
    //排序之后自然输出，只取前10部电影
    public static class MovieMR1Reducer2 extends Reducer<MovieRating, NullWritable, MovieRating, NullWritable>{
        
        Text outKey = new Text();
        int count = 0;
        
        @Override
        protected void reduce(MovieRating key, Iterable<NullWritable> values,Context context) throws IOException, InterruptedException {

            for(NullWritable value : values) {
                count++;
                if(count > 10) {
                    return;
                }
                context.write(key, value);
                
            }
        
        }
        
    }
}
