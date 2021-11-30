package myproject.demo.manager.viewManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.Age;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Preference.domain.TotalPreferenceCount.TotalPreferenceCountRepository;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HomeViewManagerService {

    private final CategoryService categoryService;
    private final CategoryNovelRelationService categoryNovelRelationService;
    private final NovelManagerService novelManagerService;
    private final NovelRepository novelRepository;
    private final TotalPreferenceCountRepository totalCountRepository;


    public List<NovelInfoDto> getTopTwentyAll() {
        return totalCountRepository.findTop20ByDeletedOrderByTotalCountDesc(false).stream()
                .map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }


    public List<NovelInfoDto> getTopTwentyAdult() {
        List<Long> adultNovelIds = novelRepository.findAllByDeletedAndAgeGreaterThanEqual(false, Age.create(18))
                .stream().map(Novel::getId).collect(Collectors.toList());
        return totalCountRepository.findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(false, adultNovelIds)
                .stream().map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }


    public List<NovelInfoDto> getTopTwentyNonAdult() {
        List<Long> nonAdultNovelIds = novelRepository.findAllByDeletedAndAgeLessThan(false, Age.create(18))
                .stream().map(Novel::getId).collect(Collectors.toList());
        return totalCountRepository.findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(false, nonAdultNovelIds)
                .stream().map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }



    public List<NovelInfoDto> getTopTwentyAllByCategory(String categoryName) {
        List<Long> novelIds = getNovelIdsCorrespondingThisCategory(categoryName);
        return totalCountRepository.findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(false, novelIds)
                .stream().map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }


    public List<NovelInfoDto> getTopTwentyAdultByCategory(String categoryName) {
        List<Long> novelIds = getNovelIdsCorrespondingThisCategory(categoryName);
        List<Long> adultNovelIds = novelRepository.findAllByDeletedAndIdInAndAgeGreaterThanEqual(false, novelIds, Age.create(18))
                .stream().map(Novel::getId).collect(Collectors.toList());
        return totalCountRepository.findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(false, adultNovelIds)
                .stream().map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }


    public List<NovelInfoDto> getTopTwentyNonAdultByCategory(String categoryName) {
        List<Long> novelIds = getNovelIdsCorrespondingThisCategory(categoryName);
        List<Long> nonAdultNovelIds = novelRepository.findAllByDeletedAndIdInAndAgeLessThan(false, novelIds, Age.create(18))
                .stream().map(Novel::getId).collect(Collectors.toList());
        return totalCountRepository.findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(false, nonAdultNovelIds)
                .stream().map(selected->novelManagerService.viewNovel(selected.getNovelId())).collect(Collectors.toList());
    }





    private List<Long> getNovelIdsCorrespondingThisCategory(String categoryName) {
        Long categoryId = categoryService.getCategoryIdByName(categoryName);
        return categoryNovelRelationService.findAllByCategoryId(categoryId);
    }
}
