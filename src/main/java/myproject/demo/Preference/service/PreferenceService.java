package myproject.demo.Preference.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoId;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
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
        checkExistence(PreferenceInfoId.create(novelId, userId, episodeId));
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(episodeId);
        PreferenceInfo preferenceInfo = PreferenceInfo.create(novelId,userId,episodeId);
        preferenceInfoRepository.save(preferenceInfo);
    }

    private void checkExistence(PreferenceInfoId preferenceInfoId) {
        if (preferenceInfoRepository.existsById(preferenceInfoId)){
            throw new IllegalArgumentException();
        }
    }
}
