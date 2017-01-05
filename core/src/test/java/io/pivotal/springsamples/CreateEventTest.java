package io.pivotal.springsamples;

import io.pivotal.springsamples.fakes.InMemoryEventRepository;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class CreateEventTest {

    private EventRepository eventRepository = new InMemoryEventRepository();
    private CreateEvent createEvent = new CreateEvent(eventRepository);
    private LocalDateTime now = LocalDateTime.of(2016, 11, 5, 13, 36, 56);
    private LocalDate tomorrow = now.plusDays(1).toLocalDate();

    @Test
    public void givenValidParameters_createsTheEvent() throws Exception {

        String createdEventId = createEvent.perform("Board Game Night", tomorrow, now, event -> {
            assertThat(event.getTitle(), equalTo("Board Game Night"));
            assertThat(event.getDate(), equalTo(tomorrow));

            return event.getId();
        });

        assertThat(createdEventId, notNullValue());
        assertThat(eventRepository.find(createdEventId).isPresent(), equalTo(true));
    }
}