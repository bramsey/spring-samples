package io.pivotal.springsamples.api;

import io.pivotal.springsamples.CreateEvent;
import io.pivotal.springsamples.EventRepository;
import io.pivotal.springsamples.FetchEvent;
import io.pivotal.springsamples.FetchUpcomingEvents;
import io.pivotal.springsamples.sql.SqlBackedEventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

/* The JPA repository interface we need Spring to implement for us is not in a sub-package of this
 * class, so we need to point Spring to its package. */
@EnableJpaRepositories("io.pivotal.springsamples.sql")
@EntityScan("io.pivotal.springsamples.sql")

/* Classes with one constructor for which Spring can satisfy all arguments (without any cleverness
 * like @Qualifier or @Profile annotations) can be @Import'ed instead of writing out @Bean methods */
@Import({
        CreateEvent.class,
        FetchEvent.class,

        SqlBackedEventRepository.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${upcoming.window}")
    private Integer upcomingWindow;

    @Bean
    public FetchUpcomingEvents fetchUpcomingEvents(EventRepository eventRepository) {
        return new FetchUpcomingEvents(eventRepository, upcomingWindow);
    }
}
