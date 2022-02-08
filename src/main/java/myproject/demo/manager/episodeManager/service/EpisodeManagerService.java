package myproject.demo.manager.episodeManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeDto;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Preference.service.PreferenceService;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.service.ViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EpisodeManagerService {

    private final EpisodeService episodeService;

    private final PreferenceService preferenceService;

    private final UpdateTimeService updateTimeService;

    private final ViewService viewCountService;

    private final NovelViewModelService novelViewModelService;

    @Transactional
    public EpisodeInfoDto createEpisode(Long novelId, String title, String content, String authorComment, int age){
        EpisodeDto episodeDto = episodeService.create(novelId, title, content, authorComment, age);
        preferenceService.createCount(novelId, episodeDto.getEpisodeId());
        viewCountService.createViewInfo(novelId, episodeDto.getEpisodeId());
        updateTimeService.update(novelId);
        novelViewModelService.increaseEpisodeCount(novelId);
        return getEpisodeInfoDto(
                novelId, episodeDto
        );
    }

    @Transactional
    public EpisodeInfoDto updateEpisode(Long novelId, int episodeId, String title, String content, String authorComment, int age){
        episodeService.update(novelId, episodeId, title, content, authorComment, age);
        updateTimeService.update(novelId);

        return getEpisodeInfoDto(
                novelId, episodeService.getEpisode(novelId, episodeId)
        );
    }

    @Transactional
    public EpisodeInfoDto deleteEpisode(Long novelId, int episodeNum){
        viewCountService.delete(novelId, episodeNum);
        preferenceService.deleteCount(novelId, episodeNum);
        episodeService.delete(novelId, episodeNum);
        novelViewModelService.decreaseEpisodeCount(novelId);
        return getEpisodeInfoDto(
                novelId, episodeService.getEpisode(novelId, episodeNum)
        );
    }

    @Transactional
    public EpisodeInfoDto resurrect(Long novelId, int episodeNum) throws IllegalAccessException {
        episodeService.resurrect(novelId, episodeNum);
        viewCountService.resurrect(novelId, episodeNum);
        preferenceService.resurrect(novelId, episodeNum);
        novelViewModelService.increaseEpisodeCount(novelId);
        return getEpisodeInfoDto(
                novelId, episodeService.getEpisode(novelId, episodeNum)
        );
    }

    private EpisodeInfoDto getEpisodeInfoDto(Long novelId, EpisodeDto episodeDto) {
        return new EpisodeInfoDto(
                novelId,
                episodeDto.getEpisodeId(),
                episodeDto.getEpisodeTitle(),
                episodeDto.getContentUrl(),
                episodeDto.getAuthorComment(),
                episodeDto.getRegistrationTime(),
                episodeDto.getAge(),
                episodeDto.isDeleted()

        );
    }
}
