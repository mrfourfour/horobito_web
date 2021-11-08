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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EditTest {

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;




    @DisplayName("Edit Test 1. Normal Condition")
    @Test
    public void test1() throws IllegalAccessException {

        NovelService sut = new NovelService(userService, novelRepository);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        UserDto userDto = new UserDto(1L, "authorName");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findByTitle(any())).thenReturn(Optional.of(novel));
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        sut.editNovel(1L, "newTitle", "newDescription", 15, "newUrl");

        assertEquals(false,  sut.editNovel(
                1L,
                "newTitle",
                "newDescription",
                15,
                "newUrl").isDeleted());

        assertEquals(1L, sut.editNovel(
                1L,
                "newTitle",
                "newDescription",
                15,
                "newUrl").getNovelId());

        assertEquals("newTitle", sut.editNovel(
                1L,
                "newTitle",
                "newDescription",
                15,
                "newUrl").getTitle());

    }


    @DisplayName("Edit Test 2. Abnormal Condition : That novel doesn't exist")
    @Test
    public void test2() throws IllegalAccessException {

        NovelService sut = new NovelService(userService, novelRepository);
        when(novelRepository.existsById(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,()-> sut.editNovel(
                1L,
                "newTitle",
                "newDescription",
                15,
                "newUrl"));

    }

    @DisplayName("Edit Test 3. Abnormal Condition : request user != novel author")
    @Test
    public void test3() throws IllegalAccessException {

        NovelService sut = new NovelService(userService, novelRepository);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        UserDto userDto = new UserDto(2L, "authorName");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findByTitle(any())).thenReturn(Optional.of(novel));
        when(userService.findLoggedUser()).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,()-> sut.editNovel(
                1L,
                "newTitle",
                "newDescription",
                15,
                "newUrl"));
    }
}
