package org.transaction.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.transaction.test.entity.User1;

@Repository
@Mapper
public interface User1Mapper {
	
	@Insert("insert into user1 (name) values (#{name})")
	int insert(User1 user);
}
