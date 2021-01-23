package com.expenses.feature.category;

import com.expenses.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Category getById(long id) {
        return findCategoryByIdAndUser(id);
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAllForUser(
                userRepository.findFirstByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()).get().getId()
        )
                .stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryDTO create(CategoryEditDTO categoryEditDTO) {
        Category category = new Category(
                categoryEditDTO.getName(),
                userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get()
        );
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory.getId(), savedCategory.getName());
    }

    public CategoryDTO update(long id, CategoryEditDTO categoryEditDTO) {
        Category category = findCategoryByIdAndUser(id);
        category.setName(categoryEditDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory.getId(), savedCategory.getName());
    }

    public void remove(long id) {
        Category category = findCategoryByIdAndUser(id);
        categoryRepository.delete(category);
    }

    private Category findCategoryByIdAndUser(long id) {
        return this.categoryRepository.findByIdAndUser(
                id,
                userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId()
        ).orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
