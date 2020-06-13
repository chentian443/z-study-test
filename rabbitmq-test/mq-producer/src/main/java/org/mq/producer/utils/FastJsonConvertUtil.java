package org.mq.producer.utils;

import org.mq.producer.entity.Order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class FastJsonConvertUtil {
    public static Order convertJSONToObject(String message,Object obj){
        Order order = JSON.parseObject(message, new TypeReference<Order>(){});
        return order;
    }

    public static String convertObjectToJSON(Object obj){
        String text = JSON.toJSONString(obj);
        return text;
    }
    
    public static void main(String[] args) {
    	Order order = convertJSONToObject("{\"name\":\"tian\"}",new Object());
    	System.out.println(order);
	}
}
