package org.mapreduce.study.course;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
public class CourseMapper extends Mapper<LongWritable, Text, Text, Text>{

	@Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		System.out.println(key);
		System.out.println(value);
		
    	String[] splits = value.toString().split(",");
    	String course = splits[0];
	    String score = splits[2];
	    Text keyOut = new Text(course);
	    Text valueOut = new Text(score);
        context.write(keyOut, valueOut);
   }
}
