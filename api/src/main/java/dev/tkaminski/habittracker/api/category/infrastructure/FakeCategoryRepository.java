package dev.tkaminski.habittracker.api.category.infrastructure;

import dev.tkaminski.habittracker.api.category.domain.Category;
import dev.tkaminski.habittracker.api.category.domain.CategoryId;
import dev.tkaminski.habittracker.api.category.domain.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FakeCategoryRepository implements CategoryRepository {

    private final Map<CategoryId, Category> categories = new ConcurrentHashMap<>();

    @Override
    public Category create(Category category) {
        CategoryId createdId = categories.keySet().stream()
                .max(Comparator.naturalOrder())
                .orElse(CategoryId.of(0))
                .next();
        Category createdCategory = new Category(createdId, category.name(), category.description());
        categories.put(createdId, createdCategory);
        return createdCategory;
    }

    @Override
    public Optional<Category> findBy(CategoryId id) {
        return Optional.ofNullable(categories.get(id));
    }

    @Override
    public Collection<Category> findAll() {
        return categories.values();
    }
}
