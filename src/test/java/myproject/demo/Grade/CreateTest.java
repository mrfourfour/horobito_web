package myproject.demo.Grade;


import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.domain.Premium;
import myproject.demo.grade.service.GradeService;
import myproject.demo.updateTime.service.UpdateTimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    GradeRepository gradeRepository;

    @Mock
    NovelService novelService;

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1() {
        GradeService sut = new GradeService(
                gradeRepository, novelService, userService);

        UserDto userDto = new UserDto(1L, "user1");
        NovelDto novelDto = new NovelDto(
                1L, 1L, "title", "descprition,"
                , "author", "url", 1, false);
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(novelService.getNovelDto(any())).thenReturn(novelDto);

        Long novelId = 1L;
        boolean premium = false;

        when(gradeRepository.saveAndFlush(any())).thenReturn(Grade.create(1L, Premium.create(premium)));
        sut.create(novelId, premium);

        verify(gradeRepository, times(1)).saveAndFlush(any());
    }

    @DisplayName("Create test 2. abnormal Condition : novel doesn't exist or novel is already deleted")
    @Test
    public void test2() {
        GradeService sut = new GradeService(
                gradeRepository, new NovelService(userService, novelRepository), userService);


        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                , 12, "url");

        when(novelRepository.existsById(any())).thenReturn(false);
        Long testNovelId = -1L;
        boolean premium = false;
        assertThrows(IllegalArgumentException.class, () -> sut.create(testNovelId, premium));


        when(novelRepository.existsById(any())).thenReturn(true);
        novel.delete();
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        Long testNovelId2 = 1L;
        assertThrows(IllegalArgumentException.class, () -> sut.create(testNovelId2, premium));
    }

    @DisplayName("Create test 3. abnormal Condition : requestUser!= author")
    @Test
    public void test3() {
        GradeService sut = new GradeService(
                gradeRepository, new NovelService(userService, novelRepository), userService);


        UserDto userDto = new UserDto(1L, "user1");
        Novel novel = NovelHelper.create(
                1L, 2L, "title", "descprition,"
                , 12, "url");

        // loggedUser
        when(userService.findLoggedUser()).thenReturn(userDto);

        // novel info
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        when(userService.findUserByUserId(any())).thenReturn(userDto); // just for username


        Long testNovelId = 1L;
        boolean premium = false;
        assertThrows(IllegalArgumentException.class, () -> sut.create(testNovelId, premium));
    }

}
