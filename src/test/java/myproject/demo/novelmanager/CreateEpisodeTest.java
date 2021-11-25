package myproject.demo.novelmanager;

import myproject.demo.manager.episodeManager.service.EpisodeManagerService;
import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
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
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.domain.ViewCountRepository;
import myproject.demo.view.service.ViewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateEpisodeTest {
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
    @Mock
    UserService userService;

    @DisplayName("CreateEpisode Test")
    @Test
    public void test1() throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);
        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);
        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);
        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository);
        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);
        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);
        ViewService viewService = new ViewService(viewCountRepository, novelService,episodeService );


        EpisodeManagerService sut = new EpisodeManagerService(
                novelService,
                categoryService,
                relationService,
                gradeService,
                episodeService,
                preferenceService,
                updateTimeService,
                bookMarkService,
                viewService
        );
        UserDto userDto = new UserDto(1L, "user1");

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);
        System.out.println(
                sut.createEpisode(14L,"episodeTitle4",
                        "episodeContent4", "authorComment4 ",12
                ));
    }
}
