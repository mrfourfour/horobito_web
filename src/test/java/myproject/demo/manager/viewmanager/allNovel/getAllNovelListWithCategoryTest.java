package myproject.demo.manager.viewmanager.allNovel;


import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.all.domain.AllNovelViewModelRepository;
import myproject.demo.manager.viewManager.all.service.AllNovelViewManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class getAllNovelListWithCategoryTest {

    @Mock
    NovelViewModelService novelViewModelService;
    @Mock
    NovelManagerService novelManagerService;
    @Mock
    AllNovelViewModelRepository allNovelViewModelRepository;
    @Mock
    CategoryService categoryService;


    @DisplayName("Test 1. check about calling correct method")
    @Test
    public void test1(){
        AllNovelViewManagerService sut = new AllNovelViewManagerService(
                novelViewModelService, novelManagerService, allNovelViewModelRepository, categoryService
        );
        Long cursor = 0L;
        int size = 20;
        when(categoryService.getCategoryIdByName(any())).thenReturn(1L);

        sut.getAllNovelListByCategory("categoryName", "view", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllInViewOrderWithCategory(1L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "preference", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllInPreferenceOrderWithCategory(1L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "date", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllInPreferenceOrderWithCategory(1L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "episode", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllInEpisodeCountOrderWithCategory(1L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "book-mark", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllInBookMarkCountOrderWithCategory(1L, cursor, size);


        ;
        assertThrows(IllegalArgumentException.class, ()->sut.getAllNovelListByCategory("temp", "wrong sort standard", cursor, size) );

    }
}
