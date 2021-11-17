package myproject.demo.BookMark;


import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMarkRepository;
import myproject.demo.bookmark.service.BookMarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
}
