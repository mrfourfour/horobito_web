package myproject.demo.manager.viewManager.all.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.all.domain.AllNovelViewModelRepository;
import myproject.demo.novelViewModel.domain.NovelViewModel;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllNovelViewManagerService {

    private final NovelViewModelService novelViewModelService;
    private final NovelManagerService novelManagerService;
    private final AllNovelViewModelRepository allNovelViewModelRepository;
    private final CategoryService categoryService;

    public List<NovelInfoDto> getAllNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }


    public List<NovelInfoDto> getAllNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(allNovelViewModelRepository.findAllAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    private Long getCategoryIdByName(String categoryName) {
        return categoryService.getCategoryIdByName(categoryName);
    }


    public List<NovelInfoDto> getNovelInfoList(List<NovelViewModel> novelViewModelList){
        return novelViewModelService.getDtoList(novelViewModelList)
                .stream().map(it->novelManagerService.viewNovel(it, it.getNovelId())).collect(Collectors.toList());
    }


}
