package org.mq.producer.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.mq.producer.constant.Constants;
import org.mq.producer.entity.BrokerMessageLog;
import org.mq.producer.entity.Order;
import org.mq.producer.mapper.BrokerMessageLogMapper;
import org.mq.producer.producer.RabbitOrderSender;
import org.mq.producer.utils.FastJsonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RetryMessageTasker {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private AtomicInteger n = new AtomicInteger(0);
	
    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    //@Scheduled(initialDelay = 5000, fixedDelay = 120000)
    public void reSend(){
        log.info("定时任务 reSend() begin to execute:{}",dateFormat.format(new Date()));
        //pull status = 0 and timeout message
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            if(messageLog.getTryCount() >= 3){
                //update fail message
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                // resend，并且让retryCount+1
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(),  new Date());
                Order reSendOrder = FastJsonConvertUtil.convertJSONToObject(messageLog.getMessage(), Order.class);
                try {
                    rabbitOrderSender.sendOrder(reSendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("定时任务 reSend()出现异常");
                }
            }
        });
    }
    
    /**
     * cron="/5 * * * * *"
     * initialDelay 启动后5s开始执行
     * fixedDelay 距上一次执行点后10s再执行，不断往复
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void test(){
    	log.info("test() execute time:{}",dateFormat.format(new Date()));
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("id", "map-" + n.addAndGet(1));
    	map.put("name", "小明");
    	map.put("address", "山东省聊城市");
    	map.put("sex", "2");
    	map.put("birth", new Date());
    	rabbitOrderSender.sendMap(map);
    }
}

