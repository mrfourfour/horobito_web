package myproject.demo.UpdateTime;


import myproject.demo.Episode.domain.Episode;
import myproject.demo.Episode.domain.EpisodeId;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.updateTime.domain.UpdateTime;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import myproject.demo.updateTime.service.UpdateTimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteTest {

    @Autowired
    UpdateTimeRepository updateTimeRepository;

    @Mock
    UserService userService;

    @Autowired
    NovelRepository novelRepository;


    @DisplayName("Delete test 1. Normal Condition")
    @Test
    public void test1() {
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, new NovelService(userService, novelRepository), userService);

        Long testNovelId = 1L; // author : 1L
        UserDto userDto = new UserDto(1L, "user1");

        //요청한 유저
        when(userService.findLoggedUser()).thenReturn(userDto);
        // 소설 작가
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        sut.delete(testNovelId);


        Optional<UpdateTime> sutObject
                = updateTimeRepository.findById(testNovelId);
        assertFalse(sutObject.isEmpty());
        assertFalse(sutObject.get().isDeleted());

        sutObject.get().delete();

        assertTrue(sutObject.get().isDeleted());

    }

    @DisplayName("Delete test 2. abnormal Condition: novel doesn't exist or already deleted ")
    @Test
    public void test2() {
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, new NovelService(userService, novelRepository), userService);

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

    @DisplayName("Delete test 3. Abnormal Condition : requestUser !=author")
    @Test
    public void test3() {
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, new NovelService(userService, novelRepository), userService);

        Long testNovelId = 1L; // author : 1L

        //요청한 유저 정보
        UserDto userDto = new UserDto(2L, "user1");


        //요청한 유저 정보
        when(userService.findLoggedUser()).thenReturn(userDto);

        // 임시
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        assertThrows(IllegalArgumentException.class,
                ()->sut.delete(testNovelId));

    }
}
