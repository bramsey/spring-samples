package io.pivotal.springsamples;

public class FetchEvent {
    private EventRepository eventRepository;

    public FetchEvent(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public <T> T perform(String id, ResultHandler<T> resultHandler) {
        return resultHandler.foundEvent(eventRepository.find(id).get());
    }

    public interface ResultHandler<T> {
        T foundEvent(Event event);
    }
}
