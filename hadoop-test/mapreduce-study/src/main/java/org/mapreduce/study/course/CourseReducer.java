package org.mapreduce.study.course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
public class CourseReducer extends Reducer<Text,Text,Text,Text> {
	
    
	// keyin 为课程，valuein为分数
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
    	System.out.println(key);
    	System.out.println(values.toString());
    	
    	List<Integer> scoreList = new ArrayList<>();
        //scoreList.clear();
        for(Text t: values){
            scoreList.add(Integer.valueOf(t.toString()));
        }

        // 求最高成绩和最低成绩
        Integer maxScore = Collections.max(scoreList);
        Integer minScore = Collections.min(scoreList);

        int sumScore = 0;
        for(int score: scoreList){
            sumScore += score;
        }

        // 求平均成绩
        double avgScore = sumScore *1D / scoreList.size();
        Text valueOut = new Text(maxScore + "\t" + minScore + "\t" + Math.round(avgScore));
        context.write(key, valueOut);
    }

}
