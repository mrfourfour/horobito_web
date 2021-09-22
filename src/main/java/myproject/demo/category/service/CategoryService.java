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
        checkAlreadyExistence(name);
        categoryRepository.save(Category.create(CategoryName.create(name)));
    }

    private void checkAlreadyExistence(String name) {
        if (categoryRepository.existsByCategoryName(CategoryName.create(name))){
            throw new IllegalArgumentException();
        }
    }
}
