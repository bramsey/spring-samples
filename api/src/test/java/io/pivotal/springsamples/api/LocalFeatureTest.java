package io.pivotal.springsamples.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(properties = {
        "upcoming.window=1"
})
public class LocalFeatureTest extends FeatureTest {

    @Value("${local.server.port}")
    private String port;

    @Override
    protected String rootUrl() {
        return "http://localhost:" + port;
    }

    @Override
    protected Integer upcomingWindow() {
        return 1;
    }

}
