package io.pivotal.springsamples.sql;

import org.springframework.data.repository.CrudRepository;

public interface EventJpaRepository extends CrudRepository<EventJpaEntity, String> {
}
