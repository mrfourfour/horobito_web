package myproject.demo.manager.viewManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelDto;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HomeViewManagerService {

    private final NovelManagerService novelManagerService;
    private final NovelViewModelService novelViewModelService;
    private final CategoryService categoryService;




    public List<NovelInfoDto> getTopTwentyAll() {
        return getResult(novelViewModelService.getTopTwentyAll());
    }

    public List<NovelInfoDto> getTopTwentyAdult() {
        return getResult(novelViewModelService.getTopTwentyAdult());
    }


    public List<NovelInfoDto> getTopTwentyNonAdult() {
        return getResult(novelViewModelService.getTopTwentyNonAdult());
    }

    public List<NovelInfoDto> getTopTwentyAllByCategory(String categoryName) {
        Long categoryId = categoryService.getCategoryIdByName(categoryName);
        return getResult(novelViewModelService.getTopTwentyAllByCategory(categoryId));
    }


    public List<NovelInfoDto> getTopTwentyAdultByCategory(String categoryName) {
        Long categoryId = categoryService.getCategoryIdByName(categoryName);
        return getResult(novelViewModelService.getTopTwentyAdultByCategory(categoryId));
    }


    public List<NovelInfoDto> getTopTwentyNonAdultByCategory(String categoryName) {
        Long categoryId = categoryService.getCategoryIdByName(categoryName);
        return getResult(novelViewModelService.getTopTwentyNonAdultByCategory(categoryId));
    }

    public List<NovelInfoDto> getResult(List<NovelViewModelDto> novelViewModelDto){
        return novelViewModelDto.stream().map(
                it->novelManagerService.viewNovel(it, it.getNovelId())
        ).collect(Collectors.toList());
    }






}
