package myproject.demo.Category_novel_relation;


import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryDto;
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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UpdateTest {

    @Autowired
    CategoryNovelRelationRepository relationRepository;

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Mock
    UserService userService;

    @DisplayName("Update test 1. Normal Condition")
    @Test
    public void test1() {


        CategoryNovelRelationService sut
                = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                new CategoryService(categoryRepository)
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);


        sut.update(9L, dtoList);


    }
}
