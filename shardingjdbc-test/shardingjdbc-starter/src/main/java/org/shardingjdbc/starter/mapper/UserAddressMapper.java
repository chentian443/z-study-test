package org.shardingjdbc.starter.mapper;

import org.shardingjdbc.starter.entity.UserAddress;

public interface UserAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
    
    
    UserAddress selectByUserId(Long userId);
}