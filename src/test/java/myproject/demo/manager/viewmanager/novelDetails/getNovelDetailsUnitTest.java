package myproject.demo.manager.viewmanager.novelDetails;


import myproject.demo.Episode.service.EpisodeDto;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.domain.*;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.viewManager.novelDetails.service.NovelDetailsViewManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelDto;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class getNovelDetailsUnitTest {

    @Mock
    NovelService novelService;
    @Mock
    UserService userService;
    @Mock
    CategoryService categoryService;
    @Mock
    EpisodeService episodeService;

    @Mock
    BookMarkService bookMarkService;

    @Mock
    NovelViewModelService novelViewModelService;

    @Mock
    NovelRepository novelRepository;


    @DisplayName("Normal Condition")
    @Test
    public void test1(){
        NovelDetailsViewManagerService sut
                = new NovelDetailsViewManagerService(
                        novelService,
                userService,
                categoryService,
                episodeService,
                bookMarkService,
                novelViewModelService
        );


        List<CategoryDto> categoryDtoList = null;


        List<EpisodeDto> episodeDtoList = null;


        UserDto userDto = new UserDto(1L, "testUser");
        NovelViewModelDto novelViewModelDto = new NovelViewModelDto(
                2L, "temp", "temp", "temp", "temp", false, 12, false, LocalDateTime.now(), 2L, 1, 2L, 1
        );


        Long existNovelId = 2L;
        when(novelViewModelService.getViewModel(any())).thenReturn(novelViewModelDto);
        when(categoryService.findAllCategoryByNovelId(any())).thenReturn(categoryDtoList);
        when(episodeService.findAllByNovelId(any())).thenReturn(episodeDtoList);

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(bookMarkService.checkUserAlreadyBookmark(any(), any())).thenReturn(false);



         sut.getNovelDetails(2L);

        verify(novelViewModelService, times(1)).getViewModel(existNovelId);
        verify(categoryService, times(1)).findAllCategoryByNovelId(existNovelId);
        verify(episodeService, times(1)).findAllByNovelId(existNovelId);
        verify(bookMarkService, times(1)).checkUserAlreadyBookmark(any(), any());

    }

    @DisplayName("Abnormal Condition 1. Novel doesn't exist")
    @Test
    public void test2(){
        NovelDetailsViewManagerService sut
                = new NovelDetailsViewManagerService(
                new NovelService(userService, novelRepository),
                userService,
                categoryService,
                episodeService,
                bookMarkService,
                novelViewModelService
        );

        when(novelRepository.existsById(any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, ()-> sut.getNovelDetails(any()));

    }

    @DisplayName("Abnormal Condition 1. Novel is already deleted")
    @Test
    public void test3(){
        NovelDetailsViewManagerService sut
                = new NovelDetailsViewManagerService(
                new NovelService(userService, novelRepository),
                userService,
                categoryService,
                episodeService,
                bookMarkService,
                novelViewModelService
        );

        Novel novel = Novel.create(Title.create("temp"), Description.create("temp"), AuthorId.create(1L), Age.create(1), CoverImageUrl.create("temp"));
        novel.delete();


        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        assertThrows(IllegalArgumentException.class, ()-> sut.getNovelDetails(any()));

    }
}
