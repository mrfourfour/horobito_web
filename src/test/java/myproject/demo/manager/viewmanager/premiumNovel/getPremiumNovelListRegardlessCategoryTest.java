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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class getPremiumNovelListRegardlessCategoryTest {

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

        sut.getAllNovelList("view", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInViewOrder(cursor, size);

        sut.getAllNovelList("preference", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInPreferenceOrder(cursor, size);

        sut.getAllNovelList("date", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInDateOrder(cursor, size);

        sut.getAllNovelList("episode", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInEpisodeCountOrder(cursor, size);

        sut.getAllNovelList("book-mark", cursor, size);
        verify(premiumNovelViewModelRepository, times(1))
                .findAllPremiumNovelInBookMarkCountOrder(cursor, size);


        ;
        assertThrows(IllegalArgumentException.class, ()->sut.getAllNovelList("wrong sort standard", cursor, size) );

    }






















}
