package myproject.demo.Preference;


import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
import myproject.demo.Preference.service.PreferenceService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
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
    NovelService novelService;

    @Mock
    EpisodeService episodeService;

    @Mock
    UserService userService;

    @Mock
    PreferenceInfoRepository infoRepository;

    @Mock
    PreferenceCountRepository countRepository;

    @DisplayName("Create test 1. Normal Condition")
    @Test
    public void test1(){
        PreferenceService sut
                = new PreferenceService(
                        novelService,
                episodeService,
                userService,
                infoRepository,
                countRepository
        );
        UserDto userDto = new UserDto(1L, "user1");

        Long novelId = 1L;
        int episodeId = 1;

        // Create info
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(infoRepository.existsById(any())).thenReturn(false);

        sut.createInfo(novelId, episodeId);
        verify(infoRepository, times(1)).saveAndFlush(any());

        // Create Count
        when(countRepository.existsById(any())).thenReturn(false);

        sut.createCount(novelId, episodeId);
        verify(countRepository, times(1)).saveAndFlush(any());
    }
}
