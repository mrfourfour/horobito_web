package myproject.demo.category.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryName;
import myproject.demo.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(String name){
        checkAlreadyExistenceByName(name);
        categoryRepository.save(Category.create(CategoryName.create(name)));
    }

    @Transactional
    public void delete(Long categoryId){
        checkExistence(categoryId);
        Category category = categoryRepository.findById(categoryId).get();
        checkAlreadyDeleted(category);
        category.delete();
    }

    private void checkAlreadyDeleted(Category category) {
        if (category.checkDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private void checkExistence(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)){
            throw new IllegalArgumentException();
        }
    }

    private void checkAlreadyExistenceByName(String name) {
        if (categoryRepository.existsByCategoryName(CategoryName.create(name))){
            throw new IllegalArgumentException();
        }
    }


}
