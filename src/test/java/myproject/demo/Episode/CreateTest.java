package myproject.demo.Episode;


import myproject.demo.Episode.domain.Episode;
import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.category.domain.CategoryRepository;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CreateTest {

    @Autowired
    CategoryNovelRelationRepository relationRepository;

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Mock
    UserService userService;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1() {
        EpisodeService sut
                = new EpisodeService(
                new NovelService(userService, novelRepository),
                userService,
                episodeRepository
                );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        Long testNovelId = 1L;

        sut.create(testNovelId, "title1","episdoe content", "1",12);
        sut.create(testNovelId, "title2","episode content2", null,18);
        sut.create(testNovelId, "title3","episode content3", "3",15);
        sut.create(testNovelId, "title4","episode content4", "",15);
        sut.create(testNovelId, "title5","episode content5", "5", 18);

        List<Episode> list = episodeRepository.findAllByNovelId(testNovelId);

        assertEquals(5, list.size());

    }

    @DisplayName("Create test 2. abnormal Condition : novel doesn't exist")
    @Test
    public void test2() {
        EpisodeService sut
                = new EpisodeService(
                new NovelService(userService, novelRepository),
                userService,
                episodeRepository
        );

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        Long testNovelId = -1L;

        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId,"title","episdoe content", "1", 15));

    }

    @DisplayName("Create test 3. abnormal Condition : request user != author")
    @Test
    public void test3() {
        EpisodeService sut
                = new EpisodeService(
                new NovelService(userService, novelRepository),
                userService,
                episodeRepository
        );

        UserDto requestUser = new UserDto(2L, "user1");
        UserDto author = new UserDto(1L, "user1");

        // 요청자 2L: 작가 : 1L.
        when(userService.findLoggedUser()).thenReturn(requestUser);
        when(userService.findUserByUserId(any())).thenReturn(author);


        assertThrows(IllegalArgumentException.class, ()->sut.create(1L,"title","episdoe content", "1", 15));

    }
}
