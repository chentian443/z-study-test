package org.mongodb.test.dao;

import org.mongodb.test.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 会再mongodb对应数据库中创建 mongoUser集合
 * @author chent
 *
 */
@Repository
public interface MongoUserDao extends MongoRepository<MongoUser, Long> {

    /**
     * 根据字字查用户
     *
     * @param userName
     * @return
     */
    MongoUser findByUserName(String userName);

}
