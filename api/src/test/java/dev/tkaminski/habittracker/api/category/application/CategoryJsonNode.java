package dev.tkaminski.habittracker.api.category.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

class CategoryJsonNode {
    private final ObjectNode node = new ObjectMapper().createObjectNode();

    public static CategoryJsonNode builder() {
        return new CategoryJsonNode();
    }

    public CategoryJsonNode name(String name) {
        node.put("name", name);
        return this;
    }

    public CategoryJsonNode description(String name) {
        node.put("description", name);
        return this;
    }

    public JsonNode build() {
        return node.deepCopy();
    }
}
