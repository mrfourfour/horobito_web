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
public class getAllAdultNovelListWithCategoryTest {

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
        Long categoryId = 2L;
        when(categoryService.getCategoryIdByName(any())).thenReturn(2L);

        sut.getAdultNovelListByCategory("categoryName", "view", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllAdultNovelInViewOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","preference", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","date", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllAdultNovelInDateOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","episode", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","book-mark", cursor, size);
        verify(allNovelViewModelRepository, times(1))
                .findAllAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size);


        ;
        assertThrows(IllegalArgumentException.class, ()->sut.getAdultNovelListByCategory("categoryName","wrong sort standard", cursor, size) );

    }
}
