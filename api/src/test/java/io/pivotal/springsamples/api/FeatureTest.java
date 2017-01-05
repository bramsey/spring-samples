package io.pivotal.springsamples.api;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public abstract class FeatureTest {

    protected abstract String rootUrl();

    @Test
    public void mainWorkflow() throws Exception {

        String newEventUrl = given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .body("{\n" +
                        "  \"title\": \"Lunchtime Drawtime\",\n" +
                        "  \"date\": \"2016-11-05\"\n" +
                        "}")
                .when()
                .post(rootUrl() + "/api/events")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Lunchtime Drawtime"))
                .body("date", equalTo("2016-11-05"))
                .extract()
                .header("Location");

        assertThat(newEventUrl, notNullValue());

        System.out.println("URL IS:");
        System.out.println(newEventUrl);

        given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .when()
                .get(newEventUrl)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("title", equalTo("Lunchtime Drawtime"))
                .body("date", equalTo("2016-11-05"));
    }
}
