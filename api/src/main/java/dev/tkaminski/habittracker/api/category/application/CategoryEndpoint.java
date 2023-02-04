package dev.tkaminski.habittracker.api.category.application;

import dev.tkaminski.habittracker.api.category.domain.Category;
import dev.tkaminski.habittracker.api.category.domain.CategoryId;
import dev.tkaminski.habittracker.api.category.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryEndpoint {

    private final CategoryRepository repository;

    @Autowired
    public CategoryEndpoint(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryJson>> getCategories() {
        List<CategoryJson> json = repository.findAll().stream()
                .map(CategoryJson::from).collect(Collectors.toList());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryJson> getCategory(@PathVariable CategoryId id) {
        CategoryJson json = repository.findBy(id).map(CategoryJson::from)
                .orElseThrow();
        return ResponseEntity.ok(json);
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @RequestBody NewCategoryJson request
    ) {
        Category created  = repository.create(request.toModel());
        CategoryId categoryId = created.id();
        URI location = MvcUriComponentsBuilder
                .fromMethodName(CategoryEndpoint.class, "getCategory", categoryId)
                .buildAndExpand(categoryId).toUri();
        return ResponseEntity.created(location).build();
    }
}
