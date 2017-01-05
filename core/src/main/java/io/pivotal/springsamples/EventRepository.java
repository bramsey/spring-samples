package io.pivotal.springsamples;

import java.util.Optional;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> find(String id);
}
