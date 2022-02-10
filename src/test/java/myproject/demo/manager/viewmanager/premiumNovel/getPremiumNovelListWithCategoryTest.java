package myproject.demo.manager.viewmanager.premiumNovel;


import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.all.domain.AllNovelViewModelRepository;
import myproject.demo.manager.viewManager.all.service.AllNovelViewManagerService;
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
public class getPremiumNovelListWithCategoryTest {

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

        when(categoryService.getCategoryIdByName(any())).thenReturn(4L);

        sut.getAllNovelListByCategory("categoryName", "view", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInViewOrderWithCategory(4L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "preference", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInPreferenceOrderWithCategory(4L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "date", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInDateOrderWithCategory(4L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "episode", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInEpisodeCountOrderWithCategory(4L, cursor, size);

        sut.getAllNovelListByCategory("categoryName", "book-mark", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInBookMarkCountOrderWithCategory(4L, cursor, size);


        ;
        assertThrows(IllegalArgumentException.class, ()->sut.getAllNovelListByCategory("temp", "wrong sort standard", cursor, size) );

    }
}
