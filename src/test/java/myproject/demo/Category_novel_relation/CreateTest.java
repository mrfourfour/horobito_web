package myproject.demo.Category_novel_relation;


import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    NovelRepository novelRepository;


    @Mock
    CategoryNovelRelationRepository relationRepository;


    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1(){
        CategoryNovelRelationService sut = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                categoryService
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        sut.create(1L, dtoList);
        verify(relationRepository, times(3)).existsById(any());
        verify(relationRepository, times(1)).saveAllAndFlush(any());

    }

    @DisplayName("Create test 1-2. Normal Condition : something already exist")
    @Test
    public void test2(){
        CategoryNovelRelationService sut = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                categoryService
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        when(relationRepository.existsById(
                CategoryNovelRelationId.create(2L, 1L)
        )).thenReturn(true);


        sut.create(1L, dtoList);
        verify(relationRepository, times(3)).existsById(any());
        verify(relationRepository, times(1)).saveAllAndFlush(any());

    }

    @DisplayName("Create test 2. Abnormal Condition : novel is already deleted")
    @Test
    public void test3(){

        CategoryNovelRelationService sut = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                categoryService
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        novel.delete();

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        assertThrows(IllegalArgumentException.class, ()-> sut.create(1L, dtoList));



    }

    @DisplayName("Create test 3. Abnormal Condition : novel doesn't exist")
    @Test
    public void test4(){

        CategoryNovelRelationService sut = new CategoryNovelRelationService(
                relationRepository,
                userService,
                new NovelService(userService, novelRepository),
                categoryService
        );

        CategoryDto dto1 = new CategoryDto(2L, "c2");
        CategoryDto dto2 = new CategoryDto(3L, "c3");
        CategoryDto dto3 = new CategoryDto(4L, "c4");
        List<CategoryDto> dtoList = new LinkedList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");


        when(novelRepository.existsById(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, ()-> sut.create(1L, dtoList));



    }
}
