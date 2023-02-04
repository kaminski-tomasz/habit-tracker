package dev.tkaminski.habittracker.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseExtractor;

import java.net.URI;
import java.util.Optional;

@Component
public class TestHttpClient {

    @Autowired
    private TestRestTemplate restTemplate;

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public static <T> String locationOf(ResponseEntity<T> response) {
        return Optional.ofNullable(response.getHeaders().getLocation())
                .map(URI::toString).orElseThrow();
    }

    public String urlFrom(String path) {
        return "http://localhost:" + port + path;
    }

    public ResponseEntity<JsonNode> get(String url, Object... urlParams) {
        return restTemplate.getForEntity(url, JsonNode.class, urlParams);
    }

    public ResponseEntity<JsonNode> post(String url, Object request, Object... urlParams) {
        return restTemplate.postForEntity(url, request, JsonNode.class, urlParams);
    }

    public ResponseEntity<Void> delete(String url, Object... params) {
        ResponseExtractor<ResponseEntity<Void>> responseExtractor =
                restTemplate.getRestTemplate().responseEntityExtractor(Void.class);
        return restTemplate.execute(url, HttpMethod.DELETE, null, responseExtractor, params);
    }
}
