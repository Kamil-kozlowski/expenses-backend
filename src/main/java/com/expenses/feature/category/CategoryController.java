package com.expenses.feature.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryEditDTO categoryEditDTO) {
        return categoryService.create(categoryEditDTO);
    }

    @PutMapping(path = "/{id}")
    public CategoryDTO update(@PathVariable long id, @RequestBody CategoryEditDTO categoryEditDTO) {
        return categoryService.update(id, categoryEditDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void remove(@PathVariable long id) {
        categoryService.remove(id);
    }

}
