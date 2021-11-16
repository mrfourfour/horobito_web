package myproject.demo.Grade;


import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.domain.Premium;
import myproject.demo.grade.service.GradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                ,"author", "url", 1, false);
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(novelService.getNovelDto(any())).thenReturn(novelDto);

        Long novelId = 1L;
        boolean premium = false;

        when(gradeRepository.saveAndFlush(any())).thenReturn(Grade.create(1L, Premium.create(premium)));
        sut.create(novelId, premium);

        verify(gradeRepository, times(1)).saveAndFlush(any());
    }

}
