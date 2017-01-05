package io.pivotal.springsamples.sql;

import io.pivotal.springsamples.Event;
import io.pivotal.springsamples.EventRepository;

import java.util.Optional;
import java.util.UUID;

public class SqlBackedEventRepository implements EventRepository {
    private EventJpaRepository jpaRepository;

    public SqlBackedEventRepository(EventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Event save(Event event) {
        EventJpaEntity savedEntity = jpaRepository.save(new EventJpaEntity(
                UUID.randomUUID().toString(),
                event.getTitle(),
                event.getDate()
        ));

        event.setId(savedEntity.getId());

        return event;
    }

    @Override
    public Optional<Event> find(String id) {
        return Optional.ofNullable(jpaRepository.findOne(id))
                .map(entity -> new Event(entity.getId(), entity.getTitle(), entity.getDate()));
    }
}
