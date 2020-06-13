package org.shardingjdbc.starter.mapper;

import org.apache.ibatis.annotations.Param;
import org.shardingjdbc.starter.entity.User;
import java.util.List;
import java.util.Map;
 
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);
 
    int insert(User record);
 
    int insertSelective(User record);
 
    User selectByPrimaryKey(Long userId);
 
    int updateByPrimaryKeySelective(User record);
 
    int updateByPrimaryKey(User record);
 
    List<User> listByCondition(@Param("condition") Map<String, Object> query);
 
    int count(@Param("condition") Map<String, Object> query);
}

