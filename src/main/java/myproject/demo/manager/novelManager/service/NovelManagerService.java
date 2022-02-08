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
import myproject.demo.novelViewModel.service.NovelViewModelDto;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import myproject.demo.updateTime.service.UpdateTimeDto;
import myproject.demo.updateTime.service.UpdateTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final NovelViewModelService novelViewModelService;
    private final BookMarkService bookMarkService;


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

        novelViewModelService.create(
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
                0);

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
                                        List<String> categories, int age, boolean premium, String coverImageUrl){
        categoryService.createAll(categories);
        List<CategoryDto> categoryDtos = categoryService.findAllByCategoryNames(categories);
        categoryNovelRelationService.update(novelId, categoryDtos);
        UpdateTimeDto updateTimeDto = updateTimeService.getUpdateTime(novelId);
        gradeService.changePremium(novelId, premium);

        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);
        novelViewModelService.updateNovelDomainArea(novelId, title, novelViewModelDto.getAuthorName(),
                coverImageUrl,description, false, age);
        novelViewModelService.updatePremium(novelId, premium);
        novelViewModelService.updateTime(novelId, updateTimeDto.getUpdateTime());


        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
                categoryDtos
        );
    }


    @Transactional
    public NovelInfoDto delete(Long novelId) throws IllegalAccessException {
        preferenceService.deleteTotalPreference(novelId);
        novelService.delete(novelId);

        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);
        categoryNovelRelationService.update(novelId, categoryDtos);
        novelViewModelService.delete(novelId);
        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);

        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
                categoryDtos
        );
    }

    @Transactional
    public NovelInfoDto resurrect(Long novelId) throws IllegalAccessException {
        novelService.resurrect(novelId);
        preferenceService.resurrectTotalCount(novelId);
        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);
        categoryNovelRelationService.update(novelId, categoryDtos);
        novelViewModelService.resurrect(novelId);

        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);


        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
                categoryDtos


        );
    }

    @Transactional
    public NovelInfoDto setBookmark(Long novelId) {
        bookMarkService.create(novelId);
        novelViewModelService.increaseBookmarkCount(novelId);
        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);
        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);

        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
                categoryDtos
        );
    }

    @Transactional
    public NovelInfoDto cancelBookmark(Long novelId){
        bookMarkService.delete(novelId);
        novelViewModelService.decreaseBookmarkCount(novelId);
        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);
        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);

        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
                categoryDtos
        );
    }

    public NovelInfoDto viewNovel(Long novelId) {
        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);
        NovelViewModelDto novelViewModel = novelViewModelService.getViewModel(novelId);
        return getNovelInfoDto(
                novelViewModel.getNovelId(),
                novelViewModel.getTitle(),
                novelViewModel.getAuthorName(),
                novelViewModel.getCoverImageUrl(),
                novelViewModel.getDescription(),
                novelViewModel.isDeleted(),
                novelViewModel.getAge(),
                novelViewModel.isPremium(),
                novelViewModel.getUpdateTime(),
                novelViewModel.getPreferenceCount(),
                novelViewModel.getBookMarkCount(),
                novelViewModel.getView(),
                novelViewModel.getEpisodeNumber(),
                categoryDtos
        );
    }

    private List<CategoryDto> getCategoryDtoList(Long novelId) {
        return categoryService.findAllByCategoryIds(categoryNovelRelationService.findCategoryIdsByNovelId(novelId));
    }

    public NovelInfoDto viewNovel(NovelViewModelDto novelViewModelDto, Long novelId) {
        List<CategoryDto> categoryDtos = getCategoryDtoList(novelId);
        return getNovelInfoDto(
                novelViewModelDto.getNovelId(),
                novelViewModelDto.getTitle(),
                novelViewModelDto.getAuthorName(),
                novelViewModelDto.getCoverImageUrl(),
                novelViewModelDto.getDescription(),
                novelViewModelDto.isDeleted(),
                novelViewModelDto.getAge(),
                novelViewModelDto.isPremium(),
                novelViewModelDto.getUpdateTime(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.getView(),
                novelViewModelDto.getEpisodeNumber(),
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

}
