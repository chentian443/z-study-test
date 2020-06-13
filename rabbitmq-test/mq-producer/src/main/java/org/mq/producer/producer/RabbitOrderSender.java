package org.mq.producer.producer;

import org.mq.producer.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.mq.producer.constant.Constants;
import org.mq.producer.entity.Order;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class RabbitOrderSender implements InitializingBean{
    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * confirm确认:确认是否到达交换器（需要再yml设置publisher-confirms=true，否则不执行）
     * 到达则ack=true，否则false
     */
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("cause: " + cause);
            System.err.println("ack: " + ack);
            String messageId = correlationData.getId();
            if(ack){ //confirm返回成功
            	if(!messageId.startsWith("map")){
            		log.info("changeBrokerMessageLogStatus");
                    brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            	}
                
            } else {//失败则进行具体的后续操作:重试 或者补偿等手段(如发送到一个不存在的exchange，则ack为false)
                System.err.println("异常处理...");
            }
        }
    };
    
    /**
     * returnConfirm机制：确认是否到达队列（需要再yml设置publisher-returns=true开始触发）
     * 注意，该方法是未到达队列时才执行
     * 如：发送消息时指定正确的exchange，错误的routingKey，则消息达到exchange而不能到达quene，此时会触发returnedMessage
     */
    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
			System.err.println("message: " + message.getBody());
            System.err.println("replyCode: " + replyCode);
            System.err.println("exchange: " + exchange);
            System.err.println("routingKey: " + routingKey);
		}
	};
    
    @Override
	public void afterPropertiesSet() throws Exception {
    	// 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        // 通过实现 ReturnCallback 接口，确认消息是否通过路由键到达队列，注意，未到达才触发
        rabbitTemplate.setReturnCallback(returnCallback);
	}

    //发送消息方法调用: 构建自定义对象消息
    public void sendOrder(Order order) throws Exception {
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
    }
    
    // test send map
    public void sendMap(Map<String, Object> map){
        CorrelationData correlationData = new CorrelationData(map.get("id").toString());
        rabbitTemplate.convertAndSend("map-exchange","map.abcd",map,correlationData);
    }

	
}
