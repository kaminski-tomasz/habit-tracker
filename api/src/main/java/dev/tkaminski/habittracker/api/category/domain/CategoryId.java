package dev.tkaminski.habittracker.api.category.domain;

import java.util.Objects;

public class CategoryId implements Comparable<CategoryId> {
    private final int value;

    private CategoryId(int value) {
        this.value = value;
    }

    public static CategoryId of(String value) {
        return new CategoryId(Integer.parseInt(value));
    }

    public static CategoryId of(int value) {
        return new CategoryId(value);
    }

    public CategoryId next() {
        return new CategoryId(value + 1);
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryId that = (CategoryId) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(CategoryId categoryId) {
        return Integer.compare(value, categoryId.value);
    }
}
