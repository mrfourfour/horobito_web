package myproject.demo.manager.episodeManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeDto;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.service.PreferenceService;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import myproject.demo.grade.service.GradeService;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.service.ViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeManagerService {

    private final NovelService novelService;

    private final CategoryService categoryService;

    private final CategoryNovelRelationService categoryNovelRelationService;

    private final GradeService gradeService;

    private final EpisodeService episodeService;

    private final PreferenceService preferenceService;


    private final UpdateTimeService updateTimeService;

    private final BookMarkService bookMarkService;

    private final ViewService viewCountService;

    @Transactional
    public EpisodeInfoDto createEpisode(Long novelId, String title, String content, String authorComment, int age) throws IllegalAccessException {
        EpisodeDto episodeDto = episodeService.create(novelId, title, content, authorComment, age);
        preferenceService.createCount(novelId, episodeDto.getEpisodeId());
        viewCountService.createViewInfo(novelId, episodeDto.getEpisodeId());
        updateTimeService.update(novelId);
        return getEpisodeInfoDto(
                novelId, episodeDto
        );
    }

    @Transactional
    public EpisodeInfoDto updateEpisode(Long novelId, int episodeId, String title, String content, String authorComment, int age) throws IllegalAccessException {
        episodeService.update(novelId, episodeId, title, content, authorComment, age);
        updateTimeService.update(novelId);

        return getEpisodeInfoDto(
                novelId, episodeService.getEpisode(novelId, episodeId)
        );
    }

    @Transactional
    public EpisodeInfoDto deleteEpisode(Long novelId, int episodeNum) throws IllegalAccessException {
        viewCountService.delete(novelId, episodeNum);
        preferenceService.deleteCount(novelId, episodeNum);
        episodeService.delete(novelId, episodeNum);
        return getEpisodeInfoDto(
                novelId, episodeService.getEpisode(novelId, episodeNum)
        );
    }

    @Transactional
    public EpisodeInfoDto resurrect(Long novelId, int episodeNum) throws IllegalAccessException {
        episodeService.resurrect(novelId, episodeNum);
        viewCountService.resurrect(novelId, episodeNum);
        preferenceService.resurrect(novelId, episodeNum);

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
