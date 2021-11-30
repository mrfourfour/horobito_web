package myproject.demo.Preference.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoId;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCount;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountId;
import myproject.demo.Preference.domain.PreferenceCount.PreferenceCountRepository;
 import myproject.demo.Preference.domain.TotalPreferenceCount.TotalPreferenceCount;
import myproject.demo.Preference.domain.TotalPreferenceCount.TotalPreferenceCountRepository;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final NovelService novelService;

    private final EpisodeService episodeService;

    private final UserService userService;


    private final PreferenceInfoRepository infoRepository;

    private final PreferenceCountRepository countRepository;

    private final TotalPreferenceCountRepository totalCountRepository;

    @Transactional
    public void createInfo(Long novelId, int episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkInfoAlreadyExist(novelId, userId, episodeId);
        infoRepository.saveAndFlush(PreferenceInfo.create(novelId, userId, episodeId));

    }

    @Transactional
    public void createTotalCount(Long novelId){
        totalCountRepository.saveAndFlush(TotalPreferenceCount.create(novelId, 0L));
    }

    @Transactional
    public void createCount(Long novelId, int episodeId){
        checkCountAlreadyExist(novelId,  episodeId);
        countRepository.saveAndFlush(PreferenceCount.create(novelId,  episodeId, 0L));

    }

    @Transactional
    public void prefer(Long novelId, int episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkCountExistence(novelId, episodeId);
        checkExistenceNovelAndEpisode(novelId, episodeId);
        checkAlreadyLiked(PreferenceInfoId.create(novelId, userId, episodeId));
        affirmInfo(novelId, userId, episodeId);
        increasePreferenceCount(novelId, episodeId);
    }

    @Transactional
    public void cancel(Long novelId, int episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        checkCountExistence(novelId, episodeId);
        checkExistenceNovelAndEpisode(novelId, episodeId);
        checkLiked(PreferenceInfoId.create(novelId, userId, episodeId));
        decreasePreferenceCount(novelId, episodeId);
        deletePreferenceInfo(novelId, episodeId, userId);
    }

    @Transactional
    public void deleteCount(Long novelId, int episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        NovelDto noveldto = novelService.getNovelDto(novelId);
        novelService.checkRequesterIdentity(userId, noveldto.getAuthorId());
        checkCountExistence(novelId, episodeId);
        deletePreferenceCounts(novelId, episodeId);


    }

    private void deletePreferenceCounts(Long novelId, int episodeId) {
        PreferenceCount deletedCount = countRepository.findById(PreferenceCountId.create(novelId, episodeId)).get();
        deletedCount.delete();
        totalCountRepository.findById(novelId).ifPresent(
                selected->
                    selected.decrease(deletedCount.getCount()) );


    }

    @Transactional
    public void deleteTotalPreference(Long novelId){
        Long userId = userService.findLoggedUser().getUserId();
        NovelDto noveldto = novelService.getNovelDto(novelId);
        novelService.checkRequesterIdentity(userId, noveldto.getAuthorId());
        totalCountRepository.findById(novelId).ifPresent(
                TotalPreferenceCount::delete);
    }

    @Transactional
    public void resurrect(Long novelId, int episodeId){
        Long userId = userService.findLoggedUser().getUserId();
        NovelDto noveldto = novelService.getNovelDto(novelId);
        novelService.checkRequesterIdentity(userId, noveldto.getAuthorId());
        checkCountExistence(novelId, episodeId);
        resurrectPreferenceCounts(novelId, episodeId);


    }

    @Transactional
    public void resurrectTotalCount(Long novelId) {
        Long userId = userService.findLoggedUser().getUserId();
        NovelDto noveldto = novelService.getNovelDto(novelId);
        novelService.checkRequesterIdentity(userId, noveldto.getAuthorId());
        totalCountRepository.findById(novelId).ifPresent(
                TotalPreferenceCount::resurrect);

    }

    private void resurrectPreferenceCounts(Long novelId, int episodeId) {
        PreferenceCount resurrectedCount = countRepository.findById(PreferenceCountId.create(novelId, episodeId)).get();
        resurrectedCount.resurrect();
        totalCountRepository.findById(novelId).ifPresent(
                selected-> selected.increase(resurrectedCount.getCount()) );
    }


    @Transactional
    public void deleteAllCount(Long novelId){
        Long userId = userService.findLoggedUser().getUserId();
        NovelDto noveldto = novelService.getNovelDto(novelId);
        novelService.checkRequesterIdentity(userId, noveldto.getAuthorId());
        countRepository.findAllByNovelId(novelId).forEach(
                selected->{
                    if (!selected.isDeleted()){
                    selected.delete();
                    }}
        );
    }


    private void affirmInfo(Long novelId, Long userId, int episodeId) {
        if (infoRepository.existsById(PreferenceInfoId.create(novelId, userId, episodeId))){
            Optional<PreferenceInfo> info = infoRepository.findById(
                    PreferenceInfoId.create(novelId, userId, episodeId));
            if (info.get().checkDeleted()){
                info.get().resurrect();
                info.get().renewalPreferenceTime();
            }
        }else {
            infoRepository.save(PreferenceInfo.create(novelId, userId, episodeId));
        }
    }

    private void checkCountExistence(Long novelId,  int episodeId) {
        if (!countRepository.existsById(PreferenceCountId.create(novelId, episodeId))){
            throw new IllegalArgumentException();
        }
        if (!totalCountRepository.existsById(novelId)){
            throw new IllegalArgumentException();
        }
    }

    private void checkCountAlreadyExist(Long novelId, int episodeId) {
        if (countRepository.existsById(PreferenceCountId.create(novelId, episodeId))){
            throw new IllegalArgumentException();
        }
    }

    private void checkInfoAlreadyExist(Long novelId, Long userId, int episodeId) {
        if (infoRepository.existsById(PreferenceInfoId.create(novelId, userId, episodeId))){
            throw new IllegalArgumentException();
        };

    }


    private void decreasePreferenceCount(Long novelId, int episodeId) {
        countRepository.findById(PreferenceCountId.create(novelId,episodeId)).get().decrease();
        totalCountRepository.findById(novelId).ifPresent(TotalPreferenceCount::decrease);
    }

    public PreferenceCountDto getTotalPreferenceCountByNovelId(Long novelId){
        return getPreferenceCountDto(totalCountRepository.findById(novelId).get().getTotalCount());

    }

    private PreferenceCountDto getPreferenceCountDto(Long count) {
        return new PreferenceCountDto(count);
    }


    private void deletePreferenceInfo(Long novelId, int episodeId, Long userId) {
        Optional<PreferenceInfo> info = infoRepository.findById(PreferenceInfoId.create(novelId, userId, episodeId));
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

    private void checkPreferenceInfoExistence(Long novelId, Long userId, int episodeId) {
        if (!infoRepository.existsById(PreferenceInfoId.create(novelId, userId, episodeId))
                || infoRepository.findById(PreferenceInfoId.create(novelId, userId, episodeId)).get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private void checkLiked(PreferenceInfoId preferenceInfoId) {
        if (!infoRepository.existsById(preferenceInfoId)
                || infoRepository.findById(preferenceInfoId).get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private void createPreferenceInfo(Long novelId, Long userId, int episodeId) {
        PreferenceInfo preferenceInfo = PreferenceInfo.create(novelId,userId,episodeId);
        infoRepository.save(preferenceInfo);
    }

    private void checkExistenceNovelAndEpisode(Long novelId, int episodeId) {
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
    }

    private void increasePreferenceCount(Long novelId, int episodeId) {
        countRepository.findById(PreferenceCountId.create(novelId,episodeId)).ifPresent(
                PreferenceCount::increase);
        totalCountRepository.findById(novelId).ifPresent(TotalPreferenceCount::increase);
    }

    private void checkAlreadyLiked(PreferenceInfoId preferenceInfoId) {
        if (infoRepository.existsById(preferenceInfoId)
                && !infoRepository.findById(preferenceInfoId).get().checkDeleted()){
            throw new IllegalArgumentException();
        }
    }
}
