package myproject.demo.novelmanager;


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
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.domain.ViewCountRepository;
import myproject.demo.view.service.ViewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateNovelTest {


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
    @Mock
    UserService userService;



    @Test
    public void testTemp(){
        for (int i =16; i<=30; i++){
            test1(i);
        }
    }

    @DisplayName("Create Test")
    @Test
    public void test1(int number){
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
        BookMarkService bookMarkService = new BookMarkService(userService,novelService,bookMarkRepository);
        ViewService viewService = new ViewService(viewCountRepository, novelService,episodeService );


        NovelManagerService sut = new NovelManagerService(
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

        String title = "Title" + number;
        String description = "Description" + number;
        List<String> categories = Arrays.asList("romance");
        int age = 12;
        boolean premium = false;
        String coverImageUrl = "Url" + number;

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);
        System.out.println(
                sut.creatNovel(
                title,description,categories,age,premium,coverImageUrl
        ));
    }


}
