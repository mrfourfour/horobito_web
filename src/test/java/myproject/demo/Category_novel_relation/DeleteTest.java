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



}
