package io.pivotal.springsamples.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pivotal.springsamples.CreateEvent;
import io.pivotal.springsamples.Event;
import io.pivotal.springsamples.FetchEvent;
import io.pivotal.springsamples.FetchUpcomingEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
public class EventsController {

    private final CreateEvent createEvent;
    private FetchEvent fetchEvent;
    private FetchUpcomingEvents fetchUpcomingEvents;

    @Autowired
    public EventsController(CreateEvent createEvent,
                            FetchEvent fetchEvent,
                            FetchUpcomingEvents fetchUpcomingEvents) {
        this.createEvent = createEvent;
        this.fetchEvent = fetchEvent;
        this.fetchUpcomingEvents = fetchUpcomingEvents;
    }

    @RequestMapping(value = "/api/events", method = RequestMethod.POST)
    public ResponseEntity createEvent(@RequestBody CreateEventRequest request, UriComponentsBuilder uriBuilder) {
        return createEvent.perform(
                request.getTitle(),
                LocalDate.parse(request.getDate()),
                LocalDateTime.now(),
                event -> ResponseEntity
                        .created(uriBuilder.path("/api/events/" + event.getId()).build().toUri())
                        .body(new EventJson(event.getId(), request.getTitle(), request.getDate()))
        );
    }

    @RequestMapping(value = "/api/events/upcoming", method = RequestMethod.GET)
    public ResponseEntity getEvent() {
        return fetchUpcomingEvents.perform(
                LocalDate.now(),
                events -> ResponseEntity.ok(events.stream().map(EventsController::toJson).collect(Collectors.toList()))
        );
    }

    @RequestMapping(value = "/api/events/{id}", method = RequestMethod.GET)
    public ResponseEntity getEvent(@PathVariable("id") String eventId) {
        return fetchEvent.perform(
                eventId,
                event -> ResponseEntity.ok(toJson(event))
        );
    }

    private static EventJson toJson(Event event) {
        return new EventJson(event.getId(), event.getTitle(), event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private static class CreateEventRequest {
        private String title;
        private String date;

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }
    }

    private static class EventJson {
        @JsonProperty
        private String id;

        @JsonProperty
        private String title;

        @JsonProperty
        private String date;

        private EventJson(String id, String title, String date) {
            this.id = id;
            this.title = title;
            this.date = date;
        }
    }
}
