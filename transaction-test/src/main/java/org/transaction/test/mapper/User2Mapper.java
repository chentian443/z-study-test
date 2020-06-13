package org.transaction.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.transaction.test.entity.User2;

@Repository
@Mapper
public interface User2Mapper {
	
	@Insert("insert into user2 (name) values (#{name})")
	int insert(User2 user);
}
