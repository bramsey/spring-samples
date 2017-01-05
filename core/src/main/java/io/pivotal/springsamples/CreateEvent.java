package io.pivotal.springsamples;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateEvent {
    private EventRepository eventRepository;

    public CreateEvent(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public <T> T perform(String title, LocalDate date, LocalDateTime currentTime, ResultHandler<T> resultHandler) {
        Event savedEvent = eventRepository.save(new Event(title, date));
        return resultHandler.eventCreated(savedEvent);
    }

    public interface ResultHandler<T> {
        T eventCreated(Event event);
    }
}
