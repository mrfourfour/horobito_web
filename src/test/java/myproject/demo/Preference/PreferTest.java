package myproject.demo.Preference;


import myproject.demo.Episode.domain.AuthorComment;
import myproject.demo.Episode.domain.ContentURL;
import myproject.demo.Episode.domain.Episode;
import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.NovelHelper;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PreferTest {

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


    @Mock
    NovelRepository novelRepository;

    @Mock
    EpisodeRepository episodeRepository;


    @DisplayName("Prefer test 1. Normal Condition : new preference")
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
        NovelDto novelDto = new NovelDto(
                1L, 1L, "title", "descprition,"
                , "author", "url", 1, false);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        Long novelId = 1L;
        int episodeId = 1;
        PreferenceCount count = PreferenceCount.create(novelId, episodeId, 0L);
        Long priorCount = count.getCount();

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(countRepository.existsById(any())).thenReturn(true);
        when(infoRepository.existsById(any())).thenReturn(false);
        when(countRepository.findById(any())).thenReturn(Optional.of(count));

        sut.prefer(novelId, episodeId);

        verify(infoRepository, times(1)).save(any());
        assertEquals(1, priorCount+1);

    }

    @DisplayName("Prefer test 2. Normal Condition : re - preference")
    @Test
    public void test2(){
        PreferenceService sut
                = new PreferenceService(
                novelService,
                episodeService,
                userService,
                infoRepository,
                countRepository
        );
        UserDto userDto = new UserDto(1L, "user1");
        NovelDto novelDto = new NovelDto(
                1L, 1L, "title", "descprition,"
                , "author", "url", 1, false);
        Novel novel = NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        Long novelId = 1L;
        Long userId = 1L;
        int episodeId = 1;
        PreferenceCount count = PreferenceCount.create(novelId, episodeId, 0L);
        PreferenceInfo info = PreferenceInfo.create(novelId,userId, episodeId);
        info.delete();

        assertTrue(info.checkDeleted());
        Long priorCount = count.getCount();


        when(userService.findLoggedUser()).thenReturn(userDto);

        //for <checkCountExistence>
        when(countRepository.existsById(any())).thenReturn(true);

        when(infoRepository.existsById(any())).thenReturn(true);
        when(infoRepository.findById(any())).thenReturn(Optional.of(info));

        when(countRepository.findById(any())).thenReturn(Optional.of(count));

        sut.prefer(novelId, episodeId);

        assertEquals(1, priorCount+1);
        assertFalse(info.checkDeleted());

    }

    @DisplayName("Prefer test 2. abnormal Condition : novel or episode Already Deleted or non exist")
    @Test
    public void test3(){
        PreferenceService sut
                = new PreferenceService(
                new NovelService(userService, novelRepository),
                new EpisodeService(novelService, userService, episodeRepository),
                userService,
                infoRepository,
                countRepository
        );
        UserDto userDto = new UserDto(1L, "user1");
        NovelDto novelDto = new NovelDto(
                1L, 1L, "title", "descprition,"
                , "author", "url", 1, false);
        Novel novel;

        Long novelId = 1L;
        Long userId = 1L;
        int episodeId = 1;

        // 1. novel non exist
        when(userService.findLoggedUser()).thenReturn(userDto);
        when(countRepository.existsById(any())).thenReturn(true);
        when(novelRepository.existsById(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, ()->sut.prefer(novelId, episodeId));

        // 2. novel already deleted
        novel =  NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        novel.delete();
        assertTrue(novel.checkDeleted());

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(countRepository.existsById(any())).thenReturn(true);
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        assertThrows(IllegalArgumentException.class, ()->sut.prefer(novelId, episodeId));

        // 3. episode non exist
        novel =  NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(countRepository.existsById(any())).thenReturn(true);
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        when(episodeRepository.existsById(any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, ()->sut.prefer(novelId, episodeId));

        // 4. episode already deleted
        novel =  NovelHelper.create(
                1L, 1L, "title", "descprition,"
                ,12, "url");

        Episode episode = Episode.create(
                1L,
                1,
                "title",
                AuthorComment.create("A"),
                ContentURL.create("a"), 1);
        episode.delete();

        when(userService.findLoggedUser()).thenReturn(userDto);
        when(countRepository.existsById(any())).thenReturn(true);
        when(novelRepository.existsById(any())).thenReturn(true);
        when(novelRepository.findById(any())).thenReturn(Optional.of(novel));

        when(episodeRepository.existsById(any())).thenReturn(true);
        when(episodeRepository.findById(any())).thenReturn(Optional.of(episode));
        assertThrows(IllegalArgumentException.class, ()->sut.prefer(novelId, episodeId));

    }
}
