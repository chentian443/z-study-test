package org.kafka.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Consumer {

    @KafkaListener(topics = {"two-part-topic"}) // 可以监听多个topic
    public void listen(ConsumerRecord<?, ?> record){

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("---->" + record);
            System.out.println("---->" + message);
        }

    }
}
