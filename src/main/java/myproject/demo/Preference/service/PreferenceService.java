package myproject.demo.Preference.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoId;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountId;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final NovelService novelService;

    private final EpisodeService episodeService;

    private final UserService userService;


    private final PreferenceInfoRepository preferenceInfoRepository;

    private final PreferenceCountRepository preferenceCountRepository;

    @Transactional
    public void like(Long novelId, Long episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkAlreadyLiked(PreferenceInfoId.create(novelId, userId, episodeId));
        checkExistenceNovelAndEpisode(novelId, episodeId);
        createPreference(novelId,userId,episodeId);
        increasePreferenceCount(novelId, episodeId);
    }

    private void createPreference(Long novelId, Long userId, Long episodeId) {
        PreferenceInfo preferenceInfo = PreferenceInfo.create(novelId,userId,episodeId);
        preferenceInfoRepository.save(preferenceInfo);
    }

    private void checkExistenceNovelAndEpisode(Long novelId, Long episodeId) {
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(episodeId);
    }

    private void increasePreferenceCount(Long novelId, Long episodeId) {
        preferenceCountRepository.findById(PreferenceCountId.create(novelId,episodeId)).get().increase();
    }

    private void checkAlreadyLiked(PreferenceInfoId preferenceInfoId) {
        if (preferenceInfoRepository.existsById(preferenceInfoId)){
            throw new IllegalArgumentException();
        }
    }
}
