package myproject.demo.novelManager.service;


import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelManagerService {

    private final NovelService novelService;

    private final CategoryService categoryService;

    private final CategoryNovelRelationService categoryNovelRelationService;

    private final GradeService gradeService;

    private final PreferenceService preferenceService;


    private final UpdateTimeService updateTimeService;

    private final BookMarkService bookMarkService;

    private final ViewService viewCountService;

    private final EpisodeService episodeService;


    @Transactional
    public NovelInfoDto creatNovel(String title, String description,
                                                   List<String> categories, int age, boolean premium, String coverImageUrl) {
        NovelDto novelDto = novelService.createNovel(title, description, age, coverImageUrl);
        categoryService.createAll(categories);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryNames(categories);
        categoryNovelRelationService.create(novelDto.getNovelId(), categoryDtos);
        GradeDto gradeDto = gradeService.create(novelDto.getNovelId(), premium);
        UpdateTimeDto updateTimeDto = updateTimeService.create(novelDto.getNovelId());

        return getNovelInfoDto(
                novelDto, categoryDtos,
                gradeDto.isPremium(), updateTimeDto,
                0L,0,
                0L, 0);
    }

    @Transactional
    public NovelInfoDto updateNovelInfo(Long novelId, String title, String description,
                                        List<String> categories, int age, boolean premium, String coverImageUrl) throws IllegalAccessException {
        NovelDto novelDto = novelService.editNovel(novelId,title, description, age, coverImageUrl);
        gradeService.changePremium(novelId, premium);
        categoryService.createAll(categories);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryNames(categories);
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        categoryNovelRelationService.update(novelId, categoryDtos);



        return getNovelInfoDto(
                novelDto,
                categoryDtos,
                premium,
                updateTimeDto,
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId),
                episodeService.getTotalEpisode(novelId)
                );

    }

    public NovelInfoDto delete(Long novelId) throws IllegalAccessException {
        NovelDto novelDto = novelService.getNovelDto(novelId);
        novelService.delete(novelId);

        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId)) ;
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        categoryNovelRelationService.update(novelId, categoryDtos);
        GradeDto gradeDto = gradeService.getGrade(novelId);

        return getNovelInfoDto(
                novelDto,
                categoryDtos,
                gradeDto.isPremium(),
                updateTimeDto,
                preferenceService.getTotalPreferenceCountByNovelId(novelId).getCount(),
                bookMarkService.getTotalBookMarkCount(novelId),
                viewCountService.getTotalViewCount(novelId),
                episodeService.getTotalEpisode(novelId)
        );
    }

    private NovelInfoDto getNovelInfoDto(
            NovelDto novelDto,
            List<CategoryDto> categoryDtos,
            boolean premium,
            UpdateTimeDto updateTimeDto,
            Long preferenceCount,
            int bookMarkCount,
            Long view, int episodeNumber) {
        return new NovelInfoDto(
          novelDto.getNovelId(),
          novelDto.getTitle(),
                novelDto.getAuthorName(),
                novelDto.getCoverImageUrl(),
                novelDto.getDescription(),
                updateTimeDto.getUpdateTime(),
                premium,
                preferenceCount,
                bookMarkCount,
                view,
                episodeNumber,
                novelDto.isDeleted(),
                categoryDtos

        );
    }
}
