package org.batch.first.repository.write;

import org.springframework.data.jpa.repository.JpaRepository;

import org.batch.first.entity.write.People;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
