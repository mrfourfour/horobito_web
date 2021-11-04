package myproject.demo.category.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryName;
import myproject.demo.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createAll(List<String> names){
        Iterator<String> itr = names.iterator();
        while (itr.hasNext()){
            create(itr.next());
        }
    }

    @Transactional
    public void create(String name) {
        if (!checkAlreadyExistenceByName(name) && checkName(name)){
            categoryRepository.saveAndFlush(Category.create(CategoryName.create(name)));
        };
    }

    private boolean checkName(String name) {
        if (name.length()==0){
            return false;
        }
        return true;
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

    public List<CategoryDto> findAllByCategoryNames(List<String> categorieNames) {
        return categorieNames.stream().filter(this::checkName)
                .map(name->categoryRepository.findByCategoryNameAndDeleted(CategoryName.create(name), false))
                .map(category -> this.getCategoryDto(category.get())).collect(Collectors.toList());

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


    private boolean checkAlreadyExistenceByName(String name) {

        return categoryRepository.existsByCategoryName(CategoryName.create(name));

    }


    public List<CategoryDto> findAllByCategoryIds(List<Long> categoryIds) {
        return categoryIds.stream().map(id->categoryRepository.findByIdAndDeleted(id, false).get())
                .map(it->getCategoryDto(it)).collect(Collectors.toList());
    }
}
