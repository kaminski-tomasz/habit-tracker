package dev.tkaminski.habittracker.api.category.application;

import dev.tkaminski.habittracker.api.category.domain.Category;

public class CategoryJson {
    public String id;
    public String name;
    public String description;

    public static CategoryJson from(Category category) {
        CategoryJson json = new CategoryJson();
        json.id = category.id().toString();
        json.name = category.name();
        json.description = category.description();
        return json;
    }
}
