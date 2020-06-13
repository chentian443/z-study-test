package org.batch.first.repository.read;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.batch.first.entity.read.User;



public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
}
