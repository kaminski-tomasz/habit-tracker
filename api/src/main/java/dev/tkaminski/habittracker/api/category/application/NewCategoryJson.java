package dev.tkaminski.habittracker.api.category.application;

import dev.tkaminski.habittracker.api.category.domain.Category;

public class NewCategoryJson {
    public String name;
    public String description;

    public Category toModel() {
        return new Category(null, name, description);
    }
}
