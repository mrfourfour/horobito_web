package myproject.demo.manager.viewmanager.novelDetails;


import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMarkRepository;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.viewManager.novelDetails.service.NovelDetailsDto;
import myproject.demo.manager.viewManager.novelDetails.service.NovelDetailsViewManagerService;
import myproject.demo.novelViewModel.domain.NovelViewModelRepository;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class getNovelDetailsIntegrationTest {

    @Mock
    UserService userService;

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    BookMarkRepository bookMarkRepository;

    @Autowired
    NovelViewModelRepository novelViewModelRepository;

    @Test
    @DisplayName("Abnormal Condition. find deleted Novel")
    public void test1(){

        NovelService novelService = new NovelService(userService, novelRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);
        BookMarkService bookMarkService = new BookMarkService(userService, novelService, bookMarkRepository);
        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelDetailsViewManagerService sut = new NovelDetailsViewManagerService(
                novelService,
                userService,
                categoryService,
                episodeService,
                bookMarkService,
                novelViewModelService
        );
        UserDto userDto = new UserDto(1L, "testUser");
        when(userService.findLoggedUser()).thenReturn(userDto);
        assertThrows(IllegalArgumentException.class, ()->sut.getNovelDetails(1L));


    }

    @Test
    @DisplayName("Normal Condition. ")
    public void test2(){

        NovelService novelService = new NovelService(userService, novelRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        EpisodeService episodeService = new EpisodeService(novelService, userService, episodeRepository);
        BookMarkService bookMarkService = new BookMarkService(userService, novelService, bookMarkRepository);
        NovelViewModelService novelViewModelService = new NovelViewModelService(novelViewModelRepository);

        NovelDetailsViewManagerService sut = new NovelDetailsViewManagerService(
                novelService,
                userService,
                categoryService,
                episodeService,
                bookMarkService,
                novelViewModelService
        );
        UserDto userDto = new UserDto(1L, "testUser");
        when(userService.findLoggedUser()).thenReturn(userDto);

        long beforeTime = System.currentTimeMillis();
        NovelDetailsDto result = sut.getNovelDetails(2L);

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        double secDiffTime = (afterTime*1.0 - beforeTime); //두 시간에 차 계산

        System.out.println(result.toString());


    }

}
