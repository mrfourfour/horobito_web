package myproject.demo.Preference;

import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCount;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
import myproject.demo.Preference.service.PreferenceService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CancelTest {

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

    @DisplayName("Cancel test 1. Normal Condition")
    @Test
    public void test1() {
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
        Long userId = 1L;
        int episodeId = 1;
        PreferenceCount count = PreferenceCount.create(novelId, episodeId, 1L);
        PreferenceInfo info = PreferenceInfo.create(novelId, userId, episodeId);

        Long priorCount = count.getCount();
        assertFalse(info.checkDeleted());

        when(userService.findLoggedUser()).thenReturn(userDto);

        //for <checkCountExistence>
        when(countRepository.existsById(any())).thenReturn(true);

        // for <checkExistenceNovelAndEpisode>
        when(infoRepository.existsById(any())).thenReturn(true);
        when(infoRepository.findById(any())).thenReturn(Optional.of(info));
        when(countRepository.findById(any())).thenReturn(Optional.of(count));

        sut.cancel(novelId, episodeId);

        assertEquals(count.getCount(), priorCount - 1);
        assertTrue(info.checkDeleted());

    }

}
