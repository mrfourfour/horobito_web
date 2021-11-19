package myproject.demo.Category;


import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteTest {

    @Autowired
    CategoryRepository categoryRepository;




    @DisplayName("Delete test 1. Normal Condition")
    @Test
    public void test1(){


        CategoryService sutService = new CategoryService(categoryRepository);

        Optional<Category> sutObject = categoryRepository.findById(1L);
        assertTrue(sutObject.isPresent());
        assertFalse(sutObject.get().isDeleted());
        sutService.delete(1L);

        sutObject.ifPresent(Category::delete);
        assertTrue(sutObject.get().isDeleted());

    }

    @DisplayName("Delete test 2. Abnormal Condition : Already Deleted")
    @Test
    public void test2(){


        CategoryService sutService = new CategoryService(categoryRepository);

        Optional<Category> sutObject = categoryRepository.findById(1L);

        assertThrows(IllegalArgumentException.class, ()->sutService.delete(1L));
    }

    @DisplayName("Delete test 3. Abnormal Condition : Not exist")
    @Test
    public void test3(){


        CategoryService sutService = new CategoryService(categoryRepository);

        Optional<Category> sutObject = categoryRepository.findById(10L);

        assertThrows(IllegalArgumentException.class, ()->sutService.delete(1L));
    }
}
