package org.mq.producer.entity;

import java.util.Date;

import lombok.Data;

@Data
public class BrokerMessageLog {
    private String messageId;

    private String message;

    private Integer tryCount;

    private String status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

}

