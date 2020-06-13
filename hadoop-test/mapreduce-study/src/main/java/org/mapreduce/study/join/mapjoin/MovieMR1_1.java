package org.mapreduce.study.join.mapjoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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


public class MovieMR1_1 {

    public static void main(String[] args) throws Exception {
        
        if(args.length < 4) {
            args = new String[4];
            args[0] = "/movie/input/";
            args[1] = "/movie/output/";
            args[2] = "/movie/cache/movies.dat";
            args[3] = "/movie/output_last/";
        }
        
        
        Configuration conf1 = new Configuration();
        conf1.set("fs.defaultFS", "hdfs://hadoop1:9000/");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        FileSystem fs1 = FileSystem.get(conf1);
        
        
        Job job1 = Job.getInstance(conf1);
        
        job1.setJarByClass(MovieMR1_1.class);
        
        job1.setMapperClass(MoviesMapJoinRatingsMapper1.class);
        job1.setReducerClass(MovieMR1Reducer1.class);
        
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(IntWritable.class);
        
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);
        
        
        
        //缓存普通文件到task运行节点的工作目录
        URI uri = new URI("hdfs://hadoop1:9000"+args[2]);
        System.out.println(uri);
        job1.addCacheFile(uri);
        
        Path inputPath1 = new Path(args[0]);
        Path outputPath1 = new Path(args[1]);
        if(fs1.exists(outputPath1)) {
            fs1.delete(outputPath1, true);
        }
        FileInputFormat.setInputPaths(job1, inputPath1);
        FileOutputFormat.setOutputPath(job1, outputPath1);
        
        boolean isDone = job1.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
       
    }
    
    public static class MoviesMapJoinRatingsMapper1 extends Mapper<LongWritable, Text, Text, IntWritable>{
        
        //用了存放加载到内存中的movies.dat数据
        private static Map<String,String> movieMap =  new HashMap<>();
        //key：电影ID
        Text outKey = new Text();
        //value：电影名+电影类型
        IntWritable outValue = new IntWritable();
        
        
        /**
         * movies.dat:    1::Toy Story (1995)::Animation|Children's|Comedy
         * 
         * 
         * 将小表(movies.dat)中的数据预先加载到内存中去
         * */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            
            Path[] localCacheFiles = context.getLocalCacheFiles();
            
            String strPath = localCacheFiles[0].toUri().toString();
            
            BufferedReader br = new BufferedReader(new FileReader(strPath));
            String readLine;
            while((readLine = br.readLine()) != null) {
                
                String[] split = readLine.split("::");
                String movieId = split[0];
                String movieName = split[1];
                String movieType = split[2];
                
                movieMap.put(movieId, movieName+"\t"+movieType);
            }
            
            br.close();
        }
        
        
        /**
         * movies.dat:    1    ::    Toy Story (1995)    ::    Animation|Children's|Comedy    
         *                 电影ID    电影名字                    电影类型
         * 
         * ratings.dat:    1    ::    1193    ::    5    ::    978300760
         *                 用户ID    电影ID        评分        评分时间戳
         * 
         * value:    ratings.dat读取的数据
         * */
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            String[] split = value.toString().split("::");
            
            String userId = split[0];
            String movieId = split[1];
            String movieRate = split[2];
            
            //根据movieId从内存中获取电影名和类型
            String movieNameAndType = movieMap.get(movieId);
            String movieName = movieNameAndType.split("\t")[0];
            String movieType = movieNameAndType.split("\t")[1];
            
            outKey.set(movieName);
            outValue.set(Integer.parseInt(movieRate));
            
            context.write(outKey, outValue);
            
        }
            
    }

    
    public static class MovieMR1Reducer1 extends Reducer<Text, IntWritable, Text, IntWritable>{
        //每部电影评论的次数
        int count;
        //评分次数
        IntWritable outValue = new IntWritable();
        
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
            
            count = 0;
            
            for(IntWritable value : values) {
                count++;
            }
            
            outValue.set(count);
            
            context.write(key, outValue);
        }
        
    }
    
    
}
