package myproject.demo.Category;


import myproject.demo.category.domain.Category;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ResurrectTest {

    @Autowired
    CategoryRepository categoryRepository;




    @DisplayName("Resurrect test 1. Normal Condition")
    @Test
    public void test1(){


        CategoryService sutService = new CategoryService(categoryRepository);

        Optional<Category> sutObject = categoryRepository.findById(1L);
        assertTrue(sutObject.isPresent());
        assertTrue(sutObject.get().isDeleted());
        sutService.resurrect("c1");

                assertFalse(sutObject.get().isDeleted());

    }

    @DisplayName("Resurrect test 2. Abnormal Condition : Not  Deleted")
    @Test
    public void test2(){


        CategoryService sutService = new CategoryService(categoryRepository);

        Optional<Category> sutObject = categoryRepository.findById(2L);
        assertTrue(sutObject.isPresent());
        assertFalse(sutObject.get().isDeleted());

        sutService.resurrect("c2");
        assertFalse(sutObject.get().isDeleted());

    }
}
