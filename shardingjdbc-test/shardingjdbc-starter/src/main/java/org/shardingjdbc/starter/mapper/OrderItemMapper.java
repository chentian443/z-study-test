package org.shardingjdbc.starter.mapper;

import org.shardingjdbc.starter.entity.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long orderItemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    
    
    OrderItem selectByOrderId(Long orderId);
}