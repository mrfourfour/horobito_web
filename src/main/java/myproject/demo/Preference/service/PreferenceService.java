package myproject.demo.Preference.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoId;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCount;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountId;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final NovelService novelService;

    private final EpisodeService episodeService;

    private final UserService userService;


    private final PreferenceInfoRepository preferenceInfoRepository;

    private final PreferenceCountRepository preferenceCountRepository;

    @Transactional
    public void prefer(Long novelId, Long episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkAlreadyLiked(PreferenceInfoId.create(novelId, userId, episodeId));
        checkExistenceNovelAndEpisode(novelId, episodeId);
        checkPreferenceInfoExistence(novelId, userId, episodeId);
        increasePreferenceCount(novelId, episodeId);
    }

    @Transactional
    public void cancel(Long novelId, Long episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkLiked(PreferenceInfoId.create(novelId, userId, episodeId));
        checkPreferenceInfoExistence(novelId, userId, episodeId);
        decreasePreferenceCount(novelId, episodeId);
        preferenceInfoRepository.findById(PreferenceInfoId.create(novelId, userId, episodeId)).get().delete();
    }

    private void decreasePreferenceCount(Long novelId, Long episodeId) {
        preferenceCountRepository.findById(PreferenceCountId.create(novelId,episodeId)).get().decrease();
    }

    private void checkLiked(PreferenceInfoId preferenceInfoId) {
        if (!preferenceInfoRepository.existsById(preferenceInfoId)
                && preferenceInfoRepository.findById(preferenceInfoId).get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void cancelPreference(Long novelId, Long episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        deletePreferenceInfo(novelId, episodeId, userId);
    }

    public PreferenceCountDto getTotalPreferenceCountByNovelId(Long novelId){
        Long count = 0L;
        Iterator<PreferenceCount> counts = preferenceCountRepository.findAllByNovelId(novelId).iterator();
        while (counts.hasNext()){
            count+= counts.next().getCount();
        }
        return getPreferenceCountDto(count);


    }

    private PreferenceCountDto getPreferenceCountDto(Long count) {
        return new PreferenceCountDto(count);
    }


    private void deletePreferenceInfo(Long novelId, Long episodeId, Long userId) {
        Optional<PreferenceInfo> info = preferenceInfoRepository.findById(PreferenceInfoId.create(novelId, userId, episodeId));
        checkAlreadyCancelled(info);
        delete(info.get());
    }

    private void checkAlreadyCancelled(Optional<PreferenceInfo> info) {
        if (!info.isPresent()||info.get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private void delete(PreferenceInfo info) {
        info.delete();
    }

    private void checkPreferenceInfoExistence(Long novelId, Long userId, Long episodeId) {
        if (!preferenceInfoRepository.existsById(PreferenceInfoId.create(novelId, userId, episodeId))){
            preferenceInfoRepository.save(PreferenceInfo.create(novelId, userId, episodeId));
        }else {
            preferenceInfoRepository.findById(PreferenceInfoId.create(novelId, userId, episodeId)).get().resurrect();
        }
    }

    private void createPreferenceInfo(Long novelId, Long userId, Long episodeId) {
        PreferenceInfo preferenceInfo = PreferenceInfo.create(novelId,userId,episodeId);
        preferenceInfoRepository.save(preferenceInfo);
    }

    private void checkExistenceNovelAndEpisode(Long novelId, Long episodeId) {
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
    }

    private void increasePreferenceCount(Long novelId, Long episodeId) {
        preferenceCountRepository.findById(PreferenceCountId.create(novelId,episodeId)).get().increase();
    }

    private void checkAlreadyLiked(PreferenceInfoId preferenceInfoId) {
        if (preferenceInfoRepository.existsById(preferenceInfoId)
                && !preferenceInfoRepository.findById(preferenceInfoId).get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }
}
