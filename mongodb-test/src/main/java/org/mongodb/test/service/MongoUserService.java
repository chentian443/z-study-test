package org.mongodb.test.service;

import org.mongodb.test.dao.MongoUserDao;
import org.mongodb.test.model.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoUserService{
    private final MongoUserDao dao;

    @Autowired
    public MongoUserService(MongoUserDao dao) {
        this.dao = dao;
    }

    public List<MongoUser> findAll() {
        return dao.findAll();
    }

    public MongoUser findById(Long id) {
        Optional<MongoUser> optionalUser = dao.findById(id);
        return optionalUser.orElse(null);
    }

    public MongoUser findByName(String userName) {
        return dao.findByUserName(userName);
    }

    public MongoUser add(MongoUser mongoUser) {
        return dao.save(mongoUser);
    }

    public void delete(Long id) {
        Optional<MongoUser> optional = dao.findById(id);
        if (!optional.isPresent()) {
            return;
        }
        dao.delete(optional.get());
    }

    public MongoUser update(MongoUser mongoUser) {
        return dao.save(mongoUser);
    }
}
