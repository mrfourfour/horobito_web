package myproject.demo.UpdateTime;


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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTest {


    @Mock
    UpdateTimeRepository updateTimeRepository;


    @Mock
     NovelService novelService;

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1(){
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, novelService, userService);

        UserDto userDto = new UserDto(1L, "user1");
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        when(updateTimeRepository.saveAndFlush(any())).thenReturn(UpdateTime.create(1L));
        sut.create(1L);

        verify(updateTimeRepository, times(1)).saveAndFlush(any());
    }

    @DisplayName("Create test 2. abnormal Condition : novel doesn't exist or novel is already deleted")
    @Test
    public void test2(){
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, new NovelService(userService, novelRepository), userService);


        UserDto userDto = new UserDto(1L, "user1");
        NovelDto novelDto = new NovelDto(
                1L, 1L, "title", "descprition,"
                ,"author", "url", 1, false);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        when(novelRepository.existsById(any())).thenReturn(false);
        Long testNovelId = -1L;
        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId));


        when(novelRepository.existsById(any())).thenReturn(true);
        novel.delete();
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));
        Long testNovelId2 = 1L;
        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId2));
    }

    @DisplayName("Create test 3. abnormal Condition : requestUser!= author")
    @Test
    public void test3(){
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, new NovelService(userService, novelRepository), userService);


        UserDto userDto = new UserDto(1L, "user1");
        Novel novel = NovelHelper.create(
                1L, 2L, "title", "descprition,"
                ,12, "url");

        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(userService.findUserByUserId(any())).thenReturn(userDto);


        Long testNovelId = 1L;
        assertThrows(IllegalArgumentException.class, ()->sut.create(testNovelId));
    }
}
