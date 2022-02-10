package myproject.demo.manager.viewmanager.premiumNovel;


import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.premium.domain.PremiumNovelViewModelRepository;
import myproject.demo.manager.viewManager.premium.service.PremiumNovelViewManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class getAdultPremiumNovelListWithCategoryTest {

    @Mock
    NovelViewModelService novelViewModelService;
    @Mock
    NovelManagerService novelManagerService;
    @Mock
    PremiumNovelViewModelRepository premiumNovelViewModelRepository;
    @Mock
    CategoryService categoryService;
    @DisplayName("Test 1. check about calling correct method")
    @Test
    public void test1(){
        PremiumNovelViewManagerService sut = new PremiumNovelViewManagerService(
                novelViewModelService, novelManagerService, premiumNovelViewModelRepository, categoryService
        );
        Long cursor = 0L;
        int size = 20;
        Long categoryId = 4L;
        when(categoryService.getCategoryIdByName(any())).thenReturn(4L);

        sut.getAdultNovelListByCategory("categoryName", "view", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAdultNovelInViewOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","preference", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","date", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAdultNovelInDateOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","episode", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size);

        sut.getAdultNovelListByCategory("categoryName","book-mark", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size);


        ;
        assertThrows(IllegalArgumentException.class, ()->sut.getAdultNovelListByCategory("categoryName","wrong sort standard", cursor, size) );

    }
}
