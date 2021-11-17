package myproject.demo.BookMark;


import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMark;
import myproject.demo.bookmark.domain.BookMarkRepository;
import myproject.demo.bookmark.service.BookMarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    UserService userService;
    @Mock
    NovelService novelService;
    @Mock
    BookMarkRepository bookMarkRepository;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Create Test 1. Normal Condition")
    @Test
    public void test1(){
        BookMarkService sut = new BookMarkService(userService, novelService, bookMarkRepository);

        UserDto userDto = new UserDto(1L, "user1");

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(bookMarkRepository.findById(any())).thenReturn(Optional.empty());
        when(bookMarkRepository.existsById(any())).thenReturn(false);

        sut.create(1L);
        verify(bookMarkRepository, times(1)).saveAndFlush(any());

    }

    @DisplayName("Create Test 3. Normal Condition : Re-bookmark")
    @Test
    public void test3(){
        BookMarkService sut = new BookMarkService(userService, novelService, bookMarkRepository);

        UserDto userDto = new UserDto(1L, "user1");

        BookMark bookMark = BookMark.create(1L, 1L);

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(bookMarkRepository.findById(any())).thenReturn(Optional.of(bookMark));
        when(bookMarkRepository.existsById(any())).thenReturn(true);
        bookMark.delete();
        assertTrue(bookMark.isDeleted());

        sut.create(1L);
        assertFalse(bookMark.isDeleted());

    }

    @DisplayName("Create Test 2. abnormal Condition : novel doesn't exist or novel is already deleted")
    @Test
    public void test2(){
        BookMarkService sut = new BookMarkService(
                userService, new NovelService(userService, novelRepository), bookMarkRepository);

        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");
        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);

        // novel doesn't exist
        when(novelRepository.existsById(any())).thenReturn(false);
        Long testNovelId = -1L;
        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId));

        // novel already deleted
        when(novelRepository.existsById(any())).thenReturn(true);
        novel.delete();
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        Long testNovelId2 = 1L;
        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId2));

    }
}
