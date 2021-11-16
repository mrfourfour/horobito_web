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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteTest {

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

    @DisplayName("Delete test 1. Normal Condition")
    @Test
    public void test1() {
        EpisodeService sut
                = new EpisodeService(
                new NovelService(userService, novelRepository),
                userService,
                episodeRepository
        );

        Long testNovelId = 1L; // author : 1L
        int testEpisodeNum = 1;
        UserDto userDto = new UserDto(1L, "user1");


        //요청한 유저
        when(userService.findLoggedUser()).thenReturn(userDto);
        // 소설 작가
        when(userService.findUserByUserId(any())).thenReturn(userDto);


        sut.delete(testNovelId, testEpisodeNum);




    }
}
