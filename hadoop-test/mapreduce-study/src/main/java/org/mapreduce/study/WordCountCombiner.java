package org.mapreduce.study;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 
 1、Combiner和Reducer的区别在于运行的位置：Combiner是在每一个MapTask所在的节点运行，Reducer是接收全局所有Mapper的输出结果
 
 2、Combiner的输入key-value的类型就是Mapper组件输出的key-value的类型，Combiner的输出key-value要跟reducer的
	输入key-value类型要对应起来
 
 3、Combiner的使用要非常谨慎，因为实际业务场景中，大部分的Combiner的逻辑和Reducer的逻辑是一致的，
	所以，在这些业务场景中，可以直接使用Reducer充当Combiner，因为Combiner在MapReduce过程中是可选的组件，可能调用也可能不调用，
	可能调一次也可能调多次，所以：Combiner使用的原则是：有或没有都不能影响业务逻辑，都不能影响最终结果

 */
public class WordCountCombiner extends Reducer<Text,IntWritable,Text,IntWritable> {
	
	@Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int sum = 0;
		for(IntWritable v: values){
			sum += v.get();
		}
		context.write(key, new IntWritable(sum));
    }
}











