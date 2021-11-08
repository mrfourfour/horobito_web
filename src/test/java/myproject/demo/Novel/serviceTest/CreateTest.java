package myproject.demo.Novel.serviceTest;


import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.domain.Title;
import myproject.demo.Novel.service.DuplicateNovelException;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.domain.*;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import org.bouncycastle.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Create Test 1. Normal Condition")
    @Test
    public void test1(){
        NovelService sut = new NovelService(userService, novelRepository);

        UserDto userDto = new UserDto(1L, "authorName");
        when(userService.findLoggedUser()).thenReturn(userDto);
        User user = User.create(
                Username.create("username"),
                Password.create("password"),
                Authority.create("auth"),
                LocalDateTime.now(),
                Gender.valueOf(Strings.toUpperCase("male"))
                );
        when(userService.findUserByUserId(any())).thenReturn(userDto);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
        ,12, "url");

        when(novelRepository.saveAndFlush(any())).thenReturn(novel);

        sut.createNovel("title", "description", 12, "url");
        verify(novelRepository, times(1)).saveAndFlush(any());


    }


    @DisplayName("Create Test 2. Abnormal Condition : Duplicate title")
    @Test
    public void test2(){
        NovelService sut = new NovelService(userService, novelRepository);

        when(novelRepository.existsByTitle(any())).thenReturn(true);

        assertThrows(DuplicateNovelException.class, ()-> sut.createNovel("title", "description", 12, "url"));

    }
}
