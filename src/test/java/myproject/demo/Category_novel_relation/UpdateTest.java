package myproject.demo.Category_novel_relation;

import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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
                new CategoryService(categoryRepository),
                categoryRepository
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

    @DisplayName("Update test 2. abnormal Condition : Category or Novel doesn't exist or is already deleted ")
    @Test
    public void test2() {


        CategoryNovelRelationService sut
                = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                new CategoryService(categoryRepository),
                categoryRepository
        );

        CategoryDto dto1 = new CategoryDto(0L, "c2");
        List<CategoryDto> dtoList1 = new LinkedList<>();
        dtoList1.add(dto1);

        CategoryDto dto2 = new CategoryDto(3L, "c2");
        List<CategoryDto> dtoList2 = new LinkedList<>();
        dtoList2.add(dto2);

        CategoryDto dto3 = new CategoryDto(3L, "c2");
        List<CategoryDto> dtoList3 = new LinkedList<>();
        dtoList3.add(dto3);

        //category: x, novel : o
        assertThrows(IllegalArgumentException.class, ()->sut.update(9L, dtoList1));

        //category: deleted, novel : o
        assertThrows(IllegalArgumentException.class, ()->sut.update(5L, dtoList2));



        //category: o, novel : x
        assertThrows(IllegalArgumentException.class, ()->sut.update(0L, dtoList2));
        //category: o, novel : deleted
        assertThrows(IllegalArgumentException.class, ()->sut.update(4L, dtoList3));


    }
}
