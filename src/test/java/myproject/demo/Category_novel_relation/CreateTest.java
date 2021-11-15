package myproject.demo.Category_novel_relation;


import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    UserService userService;

    @Mock
    NovelService novelService;

    @Mock
    CategoryService categoryService;

    @Mock
    CategoryNovelRelationRepository relationRepository;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1(){
        CategoryNovelRelationService sut = new CategoryNovelRelationService(
                relationRepository,
                userService,
                novelService,
                categoryService
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        sut.create(1L, dtoList);
        verify(relationRepository, times(3)).existsById(any());
        verify(relationRepository, times(1)).saveAllAndFlush(any());

    }
}
