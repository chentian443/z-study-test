package org.shardingjdbc.starter.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Order {
    private Long orderId;

    private String orderNo;

    private Long userId;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private String remark;

}