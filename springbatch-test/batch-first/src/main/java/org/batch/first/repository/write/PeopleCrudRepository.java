package org.batch.first.repository.write;

import org.batch.first.entity.write.People;
import org.springframework.data.repository.CrudRepository;

/**
 * @author clyde lou
 */
public interface PeopleCrudRepository extends CrudRepository<People, Long> {
}
