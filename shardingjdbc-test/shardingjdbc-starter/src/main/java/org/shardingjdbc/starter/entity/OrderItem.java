package org.shardingjdbc.starter.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderItem {
    private Long orderItemId;

    private Long orderId;

    private Long productId;

    private BigDecimal itemPrice;

    private Integer totalNum;

    private BigDecimal totalPrice;

    private Date orderTime;

    private Long userId;

}