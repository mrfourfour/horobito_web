package myproject.demo.UpdateTime;


import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.updateTime.domain.UpdateTime;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import myproject.demo.updateTime.service.UpdateTimeService;
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
    UpdateTimeRepository updateTimeRepository;


    @Mock
     NovelService novelService;

    @Mock
    UserService userService;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1(){
        UpdateTimeService sut = new UpdateTimeService(
                updateTimeRepository, novelService, userService);


        when(updateTimeRepository.saveAndFlush(any())).thenReturn(UpdateTime.create(1L));
        sut.create(1L);

        verify(updateTimeRepository, times(1)).saveAndFlush(any());
    }
}
