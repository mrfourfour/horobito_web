package myproject.demo.BookMark;

import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMark;
import myproject.demo.bookmark.domain.BookMarkId;
import myproject.demo.bookmark.domain.BookMarkRepository;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.grade.service.GradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith({SpringExtension.class})
public class DeleteTest {

    @Mock
    UserService userService;
    @Autowired
    NovelService novelService;
    @Autowired
    BookMarkRepository bookMarkRepository;

    @Autowired
    NovelRepository novelRepository;

    @DisplayName("Deleted Test 1. Normal Condition")
    @Test
    public void test2() {
        BookMarkService sut = new BookMarkService(
                userService, new NovelService(userService, novelRepository), bookMarkRepository);


        Long novelId = 1L;

        //요청한 유저 정보
        Long userId= 1L;
        UserDto userDto = new UserDto(userId, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);

        sut.delete(novelId);

        Optional<BookMark> sutObject = bookMarkRepository.findById(BookMarkId.create(userId, novelId));

        assertTrue(sutObject.isPresent());
        assertFalse(sutObject.get().isDeleted());
        sutObject.ifPresent(BookMark::delete);
        assertTrue(sutObject.get().isDeleted());

    }

    @DisplayName("Delete test 2. abnormal Condition: novel doesn't exist or already deleted")
    @Test
    public void test3() {
        BookMarkService sut = new BookMarkService(
                userService, new NovelService(userService, novelRepository), bookMarkRepository);

        UserDto userDto = new UserDto(1L, "user1");

        //요청한 유저
        when(userService.findLoggedUser()).thenReturn(userDto);
        // 소설 작가
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        Long nonExistNovelId = -1L;
        Long deletedNovelId = 3L;

        // novel : x
        assertThrows(IllegalArgumentException.class, ()->sut.delete(nonExistNovelId));

        // novel : deleted
        assertThrows(IllegalArgumentException.class, ()->sut.delete(deletedNovelId));


    }


}
