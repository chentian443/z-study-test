package org.mapreduce.study.sort.first;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 自定义比较类
 * @author chent
 */
public class SortComparator extends WritableComparator{
	
	protected SortComparator() {
		super(Text.class, true);
	}
	
	@Override
	public int compare(WritableComparable key1, WritableComparable key2) {
		//获取key1和key2的第一第二字段值
		String[] key1_arr = key1.toString().split("\t");
		int key1_1 = Integer.parseInt(key1_arr[0]);
		int key1_2 = Integer.parseInt(key1_arr[1]);
		
		String[] key2_arr = key2.toString().split("\t");
		int key2_1 = Integer.parseInt(key2_arr[0]);
		int key2_2 = Integer.parseInt(key2_arr[1]);
		
		//如果第一字段值不同，比较第一字段值
		if (key1_1 != key2_1) {
			return key1_1 > key2_1 ? -1 : 1;
		}
		//如果第一字段值相同，比较第二字段值
		else {
			return key1_2 > key2_2 ? -1 : (key1_2 == key2_2 ? 0 : 1);
		}
	}
}
