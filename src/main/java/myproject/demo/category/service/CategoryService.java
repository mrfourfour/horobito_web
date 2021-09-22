package myproject.demo.category.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryName;
import myproject.demo.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(String name) {
        checkAlreadyExistenceByName(name);
        categoryRepository.save(Category.create(CategoryName.create(name)));
    }

    @Transactional
    public void delete(Long categoryId) {
        checkExistence(categoryId);
        Category category = categoryRepository.findById(categoryId).get();
        checkAlreadyDeleted(category);
        category.delete();
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAllByDeleted(false).stream()
                .map(this::getCategoryDto).collect(Collectors.toList());
    }

    private CategoryDto getCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()

        );
    }

    private void checkAlreadyDeleted(Category category) {
        if (category.checkDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkExistence(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException();
        }
    }

    private void checkAlreadyExistenceByName(String name) {
        if (categoryRepository.existsByCategoryName(CategoryName.create(name))) {
            throw new IllegalArgumentException();
        }
    }


}
