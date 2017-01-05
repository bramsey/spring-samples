package io.pivotal.springsamples.fakes;

import io.pivotal.springsamples.Event;
import io.pivotal.springsamples.EventRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryEventRepository implements EventRepository {

    private Map<String, Event> repo = new HashMap<>();

    @Override
    public Event save(Event event) {
        event.setId(UUID.randomUUID().toString());
        repo.put(event.getId(), event);
        return event;
    }

    @Override
    public Optional<Event> find(String id) {
        return Optional.ofNullable(repo.get(id));
    }
}
