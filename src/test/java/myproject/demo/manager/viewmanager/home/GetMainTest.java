package myproject.demo.manager.viewmanager.home;


import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
import myproject.demo.Preference.domain.TotalPreferenceCount.TotalPreferenceCountRepository;
import myproject.demo.Preference.service.PreferenceService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMarkRepository;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.service.GradeService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.home.service.HomeViewManagerService;
import myproject.demo.novelViewModel.domain.NovelViewModelRepository;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.domain.ViewCountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetMainTest {
    @Autowired
    BookMarkRepository bookMarkRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryNovelRelationRepository relationRepository;
    @Autowired
    EpisodeRepository episodeRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    NovelRepository novelRepository;
    @Autowired
    PreferenceCountRepository countRepository;
    @Autowired
    PreferenceInfoRepository infoRepository;
    @Autowired
    UpdateTimeRepository updateTimeRepository;
    @Autowired
    ViewCountRepository viewCountRepository;
    @Autowired
    TotalPreferenceCountRepository totalCountRepository;
    @Autowired
    NovelViewModelRepository novelViewModelRepository;

    @Mock
    UserService userService;

    @DisplayName("GetMain Test1. normal condition, getTop20AllAge")
    @Test
    public void test1() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);

        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
        novelService,
        categoryService,
        relationService,
        gradeService,
        preferenceService,
         updateTimeService,
         novelViewModelService,
         bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        List<NovelInfoDto> result =  sut.getTopTwentyAll();
        System.out.println(result);

    }

    @DisplayName("GetMain Test2. normal condition, getTop20Adult")
    @Test
    public void test2() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);

        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                preferenceService,
                updateTimeService,
                novelViewModelService,
                bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        List<NovelInfoDto> result =  sut.getTopTwentyAdult();
        System.out.println(result);

    }

    @DisplayName("GetMain Test3. normal condition, getTop20NonAdult")
    @Test
    public void test3() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);

        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                preferenceService,
                updateTimeService,
                novelViewModelService,
                bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        List<NovelInfoDto> result =  sut.getTopTwentyNonAdult();
        System.out.println(result);

    }

    @DisplayName("GetMain Test4. normal - getTop20AllAgeByCategory & abnormal- category doesn't exist")
    @Test
    public void test4() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);

        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                preferenceService,
                updateTimeService,
                novelViewModelService,
                bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class, ()->sut.getTopTwentyAllByCategory("notPresent"));

        List<NovelInfoDto> result =  sut.getTopTwentyAllByCategory("fantasy");
        System.out.println(result);


        List<NovelInfoDto> result2 =  sut.getTopTwentyAllByCategory("romance");
        System.out.println(result);
    }

    @DisplayName("GetMain Test5. normal - getTop20AdultByCategory & abnormal- category doesn't exist")
    @Test
    public void test5() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);

        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                preferenceService,
                updateTimeService,
                novelViewModelService,
                bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class, ()->sut.getTopTwentyAdultByCategory("notPresent"));


        List<NovelInfoDto> empty =  sut.getTopTwentyAdultByCategory("fantasy"); // 전연령 카테고리
        assertEquals(0, sut.getTopTwentyAdultByCategory("fantasy").size());

        List<NovelInfoDto> result =  sut.getTopTwentyAdultByCategory("horror"); // 성인 카테고리
        System.out.println(result);




    }

    @DisplayName("GetMain Test6. normal - getTop20NonAdultByCategory & abnormal- category doesn't exist")
    @Test
    public void test6() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);

        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);

        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);

        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalCountRepository);

        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);


        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelManagerService novelManagerService = new NovelManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                preferenceService,
                updateTimeService,
                novelViewModelService,
                bookMarkService
        );

        HomeViewManagerService sut = new HomeViewManagerService(
                novelManagerService,
                novelViewModelService,
                categoryService
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class, ()->sut.getTopTwentyNonAdultByCategory("notPresent"));

        List<NovelInfoDto> result =  sut.getTopTwentyNonAdultByCategory("romance");
        assertEquals(20, result.size());
        System.out.println(result);

        List<NovelInfoDto> empty =  sut.getTopTwentyNonAdultByCategory("parody"); // 성인 카테고리


        assertEquals(0, sut.getTopTwentyNonAdultByCategory("parody").size());

        System.out.println(empty);

    }

}
