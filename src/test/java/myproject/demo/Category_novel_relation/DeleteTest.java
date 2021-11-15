package myproject.demo.Category_novel_relation;


import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelation;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteTest {

    @Autowired
    CategoryNovelRelationRepository relationRepository;

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Mock
    UserService userService;

    @DisplayName("Delete test 1. Normal Condition")
    @Test
    public void test1(){


        CategoryNovelRelationService sut
                = new CategoryNovelRelationService(
                        relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                new CategoryService(categoryRepository)
        );

        Optional<CategoryNovelRelation> relation
                = relationRepository.findById(
                        CategoryNovelRelationId.create(1L, 1L));

        Assertions.assertTrue(relation.isPresent());
        Assertions.assertFalse(relation.get().checkDeleted());

        relation.ifPresent(
                CategoryNovelRelation::delete
        );

        Assertions.assertTrue(relation.get().checkDeleted());

    }

    @DisplayName("Delete test 2. Abnormal Condition : novel or category doesn't exist")
    @Test
    public void test2(){


        CategoryNovelRelationService sut
                = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                new CategoryService(categoryRepository)
        );

        Optional<CategoryNovelRelation> relation1
                = relationRepository.findById(
                CategoryNovelRelationId.create(1L, 0L));

        Optional<CategoryNovelRelation> relation2
                = relationRepository.findById(
                CategoryNovelRelationId.create(0L, 1L));

        Assertions.assertFalse(relation1.isPresent());
        Assertions.assertFalse(relation2.isPresent());
    }

    @DisplayName("Delete test 3. Abnormal Condition : novel or category is already deleted")
    @Test
    public void test3(){


        CategoryNovelRelationService sut
                = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                new CategoryService(categoryRepository)
        );

        Optional<CategoryNovelRelation> relation1
                = relationRepository.findById(
                CategoryNovelRelationId.create(1L, 1L));

        Optional<CategoryNovelRelation> relation2
                = relationRepository.findById(
                CategoryNovelRelationId.create(2L, 1L));


        Assertions.assertFalse(relation1.get().checkDeleted());
        Assertions.assertFalse(relation2.get().checkDeleted());

        relation1.ifPresent(CategoryNovelRelation::delete);
        relation2.ifPresent(CategoryNovelRelation::delete);

        Assertions.assertTrue(relation1.get().checkDeleted());
        Assertions.assertTrue(relation2.get().checkDeleted());

        assertThrows(IllegalArgumentException.class, ()-> sut.delete(1L, 3L));
    }



}
