package org.kafka.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.kafka.demo.entity.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: Xiaour
 * @Description:
 * @Date: 2018/5/22 15:07
 */
@Slf4j
@Component
public class Producer implements InitializingBean{

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();
    
    @Override
    public void afterPropertiesSet(){
    	System.out.println("--------------------------------------------------------------------");
    	kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
            	log.info("+++++++++++++"+topic+"++++++++++++");
            	log.info("+++++++++++++"+partition+"++++++++++++");
            	log.info("+++++++++++++"+key+"++++++++++++");
            	log.info("+++++++++++++"+value+"++++++++++++");
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
            }
            
            // 该方法已被废弃，
            @Override
            public boolean isInterestedInSuccess() {
                return false;
            }
        });
    }
    
    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg("大家好，我是一个中国人！");
        message.setSendTime(new Date());
        // 未指定分区及key，默认key为null，则该消息数据会按照轮询算法发送到该主题的所有分区。
        // 如果指定了key，则按照key的hash值计算，然后放到主题的某个分区；显然，相同key的消息总是会发送到固定的一个分区。
        kafkaTemplate.send("two-part-topic", gson.toJson(message).toString());
    }
    
	
}
