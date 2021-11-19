package myproject.demo.Novel.serviceTest;

import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
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
public class DeleteTest {

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Delete Test 1. Normal Condition")
    @Test
    public void test1() throws IllegalAccessException {
        User user = User.create(
                Username.create("username"),
                Password.create("password"),
                Authority.create("auth"),
                LocalDateTime.now(),
                Gender.valueOf(Strings.toUpperCase("male"))
        );
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");


        NovelService sut = new NovelService(userService, novelRepository);
        UserDto userDto = new UserDto(1L, "authorName");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        when(userService.findLoggedUser()).thenReturn(userDto);


        assertEquals(false, novel.isDeleted());
        sut.delete(1L);
        assertEquals(true, novel.isDeleted());


    }

    @DisplayName("Delete Test 2. Abnormal Condition : Already Deleted")
    @Test
    public void test2() throws IllegalAccessException {
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        novel.delete();

        NovelService sut = new NovelService(userService, novelRepository);
        UserDto userDto = new UserDto(1L, "authorName");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        when(userService.findLoggedUser()).thenReturn(userDto);


        assertThrows(IllegalArgumentException.class, ()->sut.delete(1L));


    }

    @DisplayName("Delete Test 3. Abnormal Condition : requestUser != author")
    @Test
    public void test3() throws IllegalAccessException {
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        NovelService sut = new NovelService(userService, novelRepository);
        UserDto userDto = new UserDto(2L, "authorName");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        when(userService.findLoggedUser()).thenReturn(userDto);


        assertThrows(IllegalArgumentException.class, ()->sut.delete(1L));


    }
}
