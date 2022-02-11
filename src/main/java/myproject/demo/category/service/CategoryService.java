package myproject.demo.category.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryName;
import myproject.demo.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
        if (checkAlreadyExistenceByName(name)
                && categoryRepository.findByCategoryName(CategoryName.create(name)).get().isDeleted()
        ){
            resurrect(name);
        }

    }

    public void resurrect(String name) {
        Optional<Category> category = categoryRepository.findByCategoryName(CategoryName.create(name));
        category.ifPresent(
                selectedCategory->{
                    if(selectedCategory.isDeleted()){
                        selectedCategory.resurrect();
                    }}
        );
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
        Optional<Category> category = categoryRepository.findById(categoryId);
        category.ifPresent(selectedCategory -> {
            checkAlreadyDeleted(selectedCategory.getId());
            selectedCategory.delete();
        });
        categoryRepository.flush();
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

    public void checkAlreadyDeleted(Long categoryId) {
        if (categoryRepository.findById(categoryId).get().isDeleted()) {
            throw new IllegalArgumentException();
        }
    }


    public void checkExistence(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException();
        }
    }


    private boolean checkAlreadyExistenceByName(String name) {

        return categoryRepository.existsByCategoryName(CategoryName.create(name));

    }


    public List<CategoryDto> findAllByCategoryIds(List<Long> categoryIds) {
        return categoryIds.stream().map(id->categoryRepository.findByIdAndDeleted(id, false))
                .filter(Optional::isPresent)
                .map(it->getCategoryDto(it.get())).collect(Collectors.toList());
    }

    public Long getCategoryIdByName(String name){
        Optional<Category> relation =
                categoryRepository.findByCategoryName(CategoryName.create(name));

        if (relation.isPresent()){
            return relation.get().getId();
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<CategoryDto> findAllCategoryByNovelId(Long novelId) {
        return categoryRepository.findAllCategoryByNovelId(novelId)
                .stream().map(this::getCategoryDto).collect(Collectors.toList());
    }
}
