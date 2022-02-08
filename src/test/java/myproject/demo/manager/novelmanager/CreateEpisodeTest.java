package myproject.demo.manager.novelmanager;

import myproject.demo.Preference.domain.TotalPreferenceCount.TotalPreferenceCountRepository;
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
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.service.GradeService;
import myproject.demo.novelViewModel.domain.NovelViewModelRepository;
import myproject.demo.novelViewModel.service.NovelViewModelService;
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
    @Autowired
    TotalPreferenceCountRepository totalPreferenceCountRepository;
    @Autowired
    NovelViewModelRepository novelViewModelRepository;
    @Mock
    UserService userService;

    @Test
    public void testCreate() throws IllegalAccessException {

        for(Long novelId =301L; novelId<=400L; novelId++){
            for (int episode =1; episode<=10; episode++){
                test1(novelId, episode);
            }
        }
    }


    @DisplayName("CreateEpisode Test")
    public void test1(Long novelId, int number) throws IllegalAccessException {
        NovelService novelService = new NovelService(userService, novelRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryNovelRelationService relationService = new CategoryNovelRelationService(
                relationRepository, userService, novelService, categoryService, categoryRepository);
        GradeService gradeService = new GradeService(gradeRepository, novelService, userService);
        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);
        PreferenceService preferenceService = new PreferenceService(
                novelService, episodeService, userService, infoRepository, countRepository, totalPreferenceCountRepository);
        UpdateTimeService updateTimeService = new UpdateTimeService(
                updateTimeRepository, novelService, userService);
        ViewService viewService = new ViewService(viewCountRepository, novelService,episodeService );
        NovelViewModelService novelViewModelService = new NovelViewModelService(
                novelViewModelRepository
        );


        EpisodeManagerService sut = new EpisodeManagerService(
                episodeService,
                preferenceService,
                updateTimeService,
                viewService,
                novelViewModelService
        );
        UserDto userDto = new UserDto(2L, "chungil");

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);
            sut.createEpisode(novelId,"episodeTitle" + number,
                        "episodeContent" + number, "authorComment" + number,18);
    }
}
