package myproject.demo.manager.viewManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelDto;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RankingViewManagerService {

    private final NovelManagerService novelManagerService;
    private final NovelViewModelService novelViewModelService;
    private final CategoryService categoryService;


    public List<NovelInfoDto> getTopAllByCategory(
            String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);

        if (categoryName.equals("all")) {
            return getResult(novelViewModelService.getRankingAll(startDay, cursor, size));
        } else {
            Long categoryId = categoryService.getCategoryIdByName(categoryName);
            return getResult(novelViewModelService.getRankingAllByCategory(startDay, categoryId, cursor, size));
        }
    }

    public List<NovelInfoDto> getTopAdultByCategory(String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);
        if (categoryName.equals("all")) {
            return getResult(novelViewModelService.getRankingAdultAll(startDay, cursor, size));
        }else {
            Long categoryId = categoryService.getCategoryIdByName(categoryName);
            return getResult(novelViewModelService.getRankingAdultByCategory(startDay, categoryId, cursor, size));
        }
    }

    public List<NovelInfoDto> getTopNonAdultByCategory(String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);
        if (categoryName.equals("all")) {
            return getResult(novelViewModelService.getRankingNonAdultAll(startDay, cursor, size));
        }else {
            Long categoryId = categoryService.getCategoryIdByName(categoryName);
            return getResult(novelViewModelService.getRankingNonAdultByCategory(startDay, categoryId, cursor, size));
        }
    }


    public List<NovelInfoDto> getResult(List<NovelViewModelDto> novelViewModelDto){
        return novelViewModelDto.stream().map(
                it->novelManagerService.viewNovel(it, it.getNovelId())
        ).collect(Collectors.toList());

    }


}




