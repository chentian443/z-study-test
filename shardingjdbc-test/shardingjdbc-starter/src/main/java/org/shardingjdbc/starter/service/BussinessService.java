package org.shardingjdbc.starter.service;

import org.shardingjdbc.starter.entity.Order;
import org.shardingjdbc.starter.entity.OrderItem;
import org.shardingjdbc.starter.entity.Product;
import org.shardingjdbc.starter.entity.User;
import org.shardingjdbc.starter.entity.UserAddress;
import org.shardingjdbc.starter.mapper.OrderItemMapper;
import org.shardingjdbc.starter.mapper.OrderMapper;
import org.shardingjdbc.starter.mapper.ProductMapper;
import org.shardingjdbc.starter.mapper.UserAddressMapper;
import org.shardingjdbc.starter.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.HashMap;
import java.util.Map;
 
@Service
public class BussinessService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAddressMapper addressMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
 
    @Transactional
    public void saveAll(User user, UserAddress address, Order order, OrderItem orderItem) {
        userMapper.insertSelective(user);
 
        address.setUserId(user.getUserId());
        addressMapper.insertSelective(address);
 
        order.setUserId(user.getUserId());
        orderMapper.insertSelective(order);
 
        orderItem.setOrderId(order.getOrderId());
        orderItem.setUserId(user.getUserId());
        orderItemMapper.insertSelective(orderItem);
    }
 
    @Transactional
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }
 
    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();
 
        Long userId = 654425839412449287L;
        User user = userMapper.selectByPrimaryKey(userId);
        result.put("user", user);
 
        UserAddress address = addressMapper.selectByUserId(userId);
        result.put("address", address);
 
        Order order = orderMapper.selectByUserId(userId);
        result.put("order", order);
 
        OrderItem orderItem = orderItemMapper.selectByOrderId(order.getOrderId());
        result.put("orderItem", orderItem);
 
        return result;
    }
}

