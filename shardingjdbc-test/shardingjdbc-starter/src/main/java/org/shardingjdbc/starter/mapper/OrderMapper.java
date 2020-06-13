package org.shardingjdbc.starter.mapper;

import org.shardingjdbc.starter.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    
    Order selectByUserId(Long userId);
}