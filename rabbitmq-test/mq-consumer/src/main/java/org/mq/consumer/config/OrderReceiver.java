package org.mq.consumer.config;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

import org.mq.producer.entity.Order;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class OrderReceiver {
	/**
	 *  @Payload注解实现反序列化，要求Order类与序列化之前的类一致（类限定名）
	 *  @Headers获取消息头信息（类似于@RequestBody）
	 * 
	 */
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue",durable = "true"),// durable=true，队列开启持久化
            exchange = @Exchange(name="order-exchange",durable = "true",type = ExchangeTypes.TOPIC),// 开启交换持久化
            key = "order.*"
        )
    )
    @RabbitHandler//如果有消息过来，在消费的时候调用这个方法
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        log.info("---------收到消息，开始消费---------");
        log.info("订单ID："+order.getId()+";name:"+order.getName());
        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);// 当前消息的index
        /**
         *  multiple 取值为 false时，不现实批量操作，表示通知 RabbitMQ 只确认收到当前消息
         *  如果为 true，则表示批量操作，将比第一个参数指定的 delivery tag小的消息一并确认
         */
        boolean multiple = false;
        /**
         * 消费者端手动确认收到消息，deliveryTag:该消息的index；multiple：是否批量. true：将一次性ack所有小于deliveryTag的消息。
         */
        channel.basicAck(deliveryTag,multiple);
        /**
         * 另外：还提供
         * basicNack(long deliveryTag, boolean multiple, boolean requeue) 与
         * basicReject(long deliveryTag, boolean requeue) 两种方法
         * basicNack方法表示失败确认，requeue=true表示失败的消息重新回到rabbitmq队列；
         * basicReject方法表示拒绝（相当于失败确认），requeue=true表示失败的消息重新回到rabbitmq队列；
         * 区别：channel.basicNack 与 channel.basicReject 的区别在于basicNack可以批量拒绝多条消息，而basicReject一次只能拒绝一条消息
         */
        
    }
    
    
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "map-queue",durable = "true"),
            exchange = @Exchange(name="map-exchange",durable = "true",type = ExchangeTypes.DIRECT),
            key = "map.abcd"
        )
    )
    @RabbitHandler
    public void onMapMessage(@Payload Map<String, Object> map, @Headers Map<String,Object> headers, Channel channel) throws IOException  {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	log.info("onMapMessage 执行消费：{}",dateFormat.format(new Date()));
    	System.out.println(map);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        boolean multiple = false;
        channel.basicAck(deliveryTag,multiple);
        log.info("onMapMessage 确认消费完毕：{}",dateFormat.format(new Date()));
    }
    
}
