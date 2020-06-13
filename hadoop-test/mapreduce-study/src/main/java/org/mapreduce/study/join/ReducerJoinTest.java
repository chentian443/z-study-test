package org.mapreduce.study.join;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * reducer端的连接都在内存中实现，map处理不同的数据集时对数据标记来源，
 * 然后在reduce函数中根据标记对数据分类，最后实现数据连接
 * 
 * @author chent
 */
public class ReducerJoinTest {

    public static void main(String[] args) throws Exception {
        
        Configuration conf1 = new Configuration();
        /*conf1.set("fs.defaultFS", "hdfs://hadoop1:9000/");
        System.setProperty("HADOOP_USER_NAME", "hadoop");*/
        FileSystem fs1 = FileSystem.get(conf1);
        Job job = Job.getInstance(conf1);
        
        job.setJarByClass(ReducerJoinTest.class);
        job.setMapperClass(MoviesMapper.class);
        job.setReducerClass(MoviesReduceJoinReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        Path inputPath1 = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\joinTest\\input\\movies.txt");
        Path inputPath2 = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\joinTest\\input\\ratings.txt");
        Path outputPath1 = new Path("D:\\BaiduNetdiskDownload\\mapreduce_test\\joinTest\\output");
        if(fs1.exists(outputPath1)) {
            fs1.delete(outputPath1, true);
        }
        
        FileInputFormat.addInputPath(job, inputPath1);
        FileInputFormat.addInputPath(job, inputPath2);
        FileOutputFormat.setOutputPath(job, outputPath1);
        
        boolean isDone = job.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
    }

    
    public static class MoviesMapper extends Mapper<LongWritable, Text, Text, Text>{
        
        Text outKey = new Text();
        Text outValue = new Text();
        StringBuilder sb = new StringBuilder();
        protected void map(LongWritable key, Text value,Context context) throws java.io.IOException ,InterruptedException {
            FileSplit inputSplit = (FileSplit)context.getInputSplit();
            String name = inputSplit.getPath().getName();
            String[] split = value.toString().split("::");
            sb.setLength(0);// 非局部变量，需要置空
            
            if(name.equals("movies.txt")) {
                //                    1　　\t　　Toy Story (1995)　　::　　Animation|Children's|Comedy
                //对应字段中文解释：　　电影ID 　　   电影名字　　　　　　　　                 电影类型
                outKey.set(split[0]);
                StringBuilder append = sb.append(split[1]).append("\t").append(split[2]);
                String str = "movies#"+append.toString();
                outValue.set(str);
                //System.out.println(outKey+"---"+outValue);
                context.write(outKey, outValue);
            }else{
                //                    1　　::　　1193　　::　　5　　::　　978300760
                //对应字段中文解释：　　用户ID　　           电影ID　　　      评分　　　　   评分时间戳
                outKey.set(split[1]);
                StringBuilder append = sb.append(split[0]).append("\t").append(split[2]).append("\t").append(split[3]);
                String str = "ratings#" + append.toString();
                outValue.set(str);
                //System.out.println(outKey+"---"+outValue);
                context.write(outKey, outValue);
            }
        
        };
        
    }
    
    
    public static class MoviesReduceJoinReducer extends Reducer<Text, Text, Text, Text>{
        //用来存放    电影ID    电影名称    电影类型    
        List<String> moviesList = new ArrayList<>();
        //用来存放    电影ID    用户ID 用户评分    时间戳
        List<String> ratingsList = new ArrayList<>();
        Text outValue = new Text();
        
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            
            //迭代集合
            for(Text text : values) {
                //将集合中的元素添加到对应的list中
                if(text.toString().startsWith("movies#")) {
                    String string = text.toString().split("#")[1];
                    moviesList.add(string);
                }else if(text.toString().startsWith("ratings#")){
                    String string = text.toString().split("#")[1];
                    ratingsList.add(string);
                }
            }
            
            //获取2个集合的长度
            long moviesSize = moviesList.size();
            long ratingsSize = ratingsList.size();
            
            for(int i=0;i<moviesSize;i++) {
                for(int j=0;j<ratingsSize;j++) {
                    outValue.set(moviesList.get(i)+"\t"+ratingsList.get(j));
                    //最后的输出是    电影ID  电影名称    电影类型    用户ID 用户评分    时间戳
                    context.write(key, outValue);
                }
            }
            
            moviesList.clear();
            ratingsList.clear();
        }
        
    }
    
}