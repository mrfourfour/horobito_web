package myproject.demo.manager.novelManager.service;


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
import myproject.demo.grade.service.GradeDto;
import myproject.demo.grade.service.GradeService;
import myproject.demo.updateTime.service.UpdateTimeDto;
import myproject.demo.updateTime.service.UpdateTimeService;
import myproject.demo.view.service.ViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class NovelManagerService {

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
    public NovelInfoDto creatNovel(String title, String description,
                                   List<String> categories, int age, boolean premium, String coverImageUrl) {
        categoryService.createAll(categories);
        NovelDto novelDto = novelService.createNovel(title, description, age, coverImageUrl);
        GradeDto gradeDto = gradeService.create(novelDto.getNovelId(), premium);
        UpdateTimeDto updateTimeDto = updateTimeService.create(novelDto.getNovelId());
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryNames(categories);
        categoryNovelRelationService.create(novelDto.getNovelId(), categoryDtos);
        preferenceService.createTotalCount(novelDto.getNovelId());

        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeDto.isPremium(),
                updateTimeDto.getUpdateTime(),
                0L,
                0,
                0L,
                0,
                categoryDtos

        );
    }

    @Transactional
    public NovelInfoDto updateNovelInfo(Long novelId, String title, String description,
                                        List<String> categories, int age, boolean premium, String coverImageUrl) throws IllegalAccessException {
        categoryService.createAll(categories);
        NovelDto novelDto = novelService.editNovel(novelId, title, description, age, coverImageUrl);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryNames(categories);
        categoryNovelRelationService.update(novelId, categoryDtos);
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        gradeService.changePremium(novelId, premium);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());


        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                premium,
                updateTimeDto.getUpdateTime(),
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos
        );
    }


    @Transactional
    public NovelInfoDto keepNovelPrivate(Long novelId) throws IllegalAccessException {
        novelService.delete(novelId);

        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());
        categoryNovelRelationService.update(novelId, categoryDtos);
        GradeDto gradeDto = gradeService.getGrade(novelId);
        NovelDto novelDto = novelService.getNovelDto(novelId);

        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeDto.isPremium(),
                updateTimeDto.getUpdateTime(),
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos


        );
    }


    @Transactional
    public NovelInfoDto delete(Long novelId) throws IllegalAccessException {
        preferenceService.deleteTotalPreference(novelId);
        novelService.delete(novelId);

        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());
        categoryNovelRelationService.update(novelId, categoryDtos);
        GradeDto gradeDto = gradeService.getGrade(novelId);
        NovelDto novelDto = novelService.getNovelDto(novelId);

        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeDto.isPremium(),
                updateTimeDto.getUpdateTime(),
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos


        );
    }

    @Transactional
    public NovelInfoDto resurrect(Long novelId) throws IllegalAccessException {
        novelService.resurrect(novelId);
        preferenceService.resurrectTotalCount(novelId);

        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());
        categoryNovelRelationService.update(novelId, categoryDtos);
        GradeDto gradeDto = gradeService.getGrade(novelId);
        NovelDto novelDto = novelService.getNovelDto(novelId);

        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeDto.isPremium(),
                updateTimeDto.getUpdateTime(),
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos


        );
    }

    public NovelInfoDto viewNovel(Long novelId) {
        NovelDto novelDto = novelService.getNovelDto(novelId);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeService.getGrade(novelId).isPremium(),
                updateTimeDto.getUpdateTime(),
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos
        );
    }


    private NovelInfoDto getNovelInfoDto(
            Long novelId,
            String title,
            String authorName,
            String coverImageUrl,
            String description,
            boolean deleted,
            int age,
            boolean premium,
            LocalDateTime updateTime,
            Long preferenceCount,
            int bookMarkCount,
            Long view,
            int episodeNumber,
            List<CategoryDto> categoryDtos
    ) {
        return new NovelInfoDto(
                novelId,
                title,
                authorName,
                coverImageUrl,
                description,
                deleted,
                age,
                premium,
                updateTime,
                preferenceCount,
                bookMarkCount,
                view,
                episodeNumber,
                categoryDtos

        );
    }

    public NovelInfoDto viewNovelWithPreference(Long novelId, Long preferenceCount) {
        NovelDto novelDto = novelService.getNovelDto(novelId);
        List<Integer> episodeIds = episodeService.findAllByNovelId(novelId)
                .stream().map(EpisodeDto::getEpisodeId).collect(Collectors.toList());
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
        return getNovelInfoDto(
                novelDto.getNovelId(),
                novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                novelDto.isDeleted(),
                novelDto.getAge(),
                gradeService.getGrade(novelId).isPremium(),
                updateTimeDto.getUpdateTime(),
                preferenceCount,
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId, episodeIds),
                episodeService.getTotalEpisode(novelId),
                categoryDtos
        );
    }
}
