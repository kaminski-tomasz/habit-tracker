package dev.tkaminski.habittracker.api.category.application;

import com.fasterxml.jackson.databind.JsonNode;
import dev.tkaminski.habittracker.api.TestHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static dev.tkaminski.habittracker.api.TestHttpClient.locationOf;
import static java.util.regex.Pattern.compile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CategoryEndpointTest {

    @Autowired
    private TestHttpClient httpClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        httpClient.setPort(port);
    }

    @Test
    void there_are_no_categories_at_beginning() {
        ResponseEntity<JsonNode> result = httpClient.get(url("/api/categories"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(0);
    }

    @Test
    void should_add_new_category() {
        JsonNode categoryNode = CategoryJsonNode.builder()
                .name("some-name")
                .description("some description")
                .build();

        ResponseEntity<JsonNode> response = httpClient.post(url("/api/categories"), categoryNode);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(locationOf(response)).containsPattern(compile("/api/categories/[0-9]+"));
    }

    @Test
    void should_load_created_category() {
        JsonNode categoryNode = CategoryJsonNode.builder()
                .name("some-name")
                .description("some description")
                .build();
        ResponseEntity<JsonNode> createResponse = httpClient.post(url("/api/categories"), categoryNode);

        ResponseEntity<JsonNode> response = httpClient.get(locationOf(createResponse));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode body = response.getBody();
        assertThat(body.get("id").isNull()).isFalse();
        assertThat(body.get("name").asText()).isEqualTo("some-name");
        assertThat(body.get("description").asText()).isEqualTo("some description");
    }

    @Test
    void should_get_all_categories() {
        addCategory(CategoryJsonNode.builder()
                .name("Internet")
                .description("Nawyk przeglądania Internetu")
                .build()
        );
        addCategory(CategoryJsonNode.builder()
                .name("Kawa")
                .description("Nawyk picia Kawy")
                .build()
        );

        ResponseEntity<JsonNode> result = httpClient.get(url("/api/categories"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody())
                .hasSize(2)
                .extracting(
                        j -> j.get("name").asText(),
                        j -> j.get("description").asText()
                )
                .contains(
                        tuple("Internet", "Nawyk przeglądania Internetu"),
                        tuple("Kawa", "Nawyk picia Kawy")
                );
    }

    String url(String path) {
        return httpClient.urlFrom(path);
    }

    private void addCategory(JsonNode categoryJson) {
        ResponseEntity<JsonNode> createResponse = httpClient.post(url("/api/categories"), categoryJson);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
