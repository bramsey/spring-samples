package io.pivotal.springsamples;

import io.pivotal.springsamples.fakes.InMemoryEventRepository;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class FetchEventTest {

    private EventRepository eventRepository = new InMemoryEventRepository();
    private FetchEvent fetchEvent = new FetchEvent(eventRepository);

    @Test
    public void givenEventExists_supplyIt() throws Exception {
        Event existingEvent = eventRepository.save(new Event("Town Hall", LocalDate.now().plusDays(7)));

        Event event = fetchEvent.perform(existingEvent.getId(), foundEvent -> foundEvent);

        assertThat(event, equalTo(existingEvent));
    }
}