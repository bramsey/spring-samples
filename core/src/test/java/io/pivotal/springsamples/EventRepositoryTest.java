package io.pivotal.springsamples;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public abstract class EventRepositoryTest {

    protected abstract EventRepository repository();

    @Test
    public void creatingAndFetchingEvents() throws Exception {
        Event savedEvent = repository().save(new Event("Board game night", LocalDate.of(2016, 11, 5)));

        Event fetchedEvent = repository().find(savedEvent.getId()).get();

        assertThat(fetchedEvent.getTitle(), equalTo("Board game night"));
        assertThat(fetchedEvent.getDate(), equalTo(LocalDate.of(2016, 11, 5)));
    }
}