package dev.tkaminski.habittracker.api.category.domain;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository {

    Category create(Category newCategory);

    Collection<Category> findAll();

    Optional<Category> findBy(CategoryId id);

    void remove(CategoryId id);
}
