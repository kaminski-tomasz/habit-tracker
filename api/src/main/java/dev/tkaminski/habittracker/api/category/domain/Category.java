package dev.tkaminski.habittracker.api.category.domain;

public class Category {
    private final CategoryId id;
    private final String name;
    private final String description;

    public Category(CategoryId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}
