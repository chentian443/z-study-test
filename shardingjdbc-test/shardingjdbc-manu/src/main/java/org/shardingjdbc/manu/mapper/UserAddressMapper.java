package org.shardingjdbc.manu.mapper;

import org.shardingjdbc.manu.entity.UserAddress;

public interface UserAddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
    
    
    UserAddress selectByUserId(Long userId);
}