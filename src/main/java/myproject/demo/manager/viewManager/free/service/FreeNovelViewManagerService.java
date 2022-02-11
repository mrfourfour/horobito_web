package myproject.demo.manager.viewManager.free.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.free.domain.FreeNovelViewManagerRepository;
import myproject.demo.novelViewModel.domain.NovelViewModel;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FreeNovelViewManagerService {


    private final NovelViewModelService novelViewModelService;
    private final NovelManagerService novelManagerService;
    private final FreeNovelViewManagerRepository freeNovelViewModelRepository;
    private final CategoryService categoryService;

    public List<NovelInfoDto> getAllNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }


    public List<NovelInfoDto> getAllNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findAllFreeNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findAdultFreeNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(freeNovelViewModelRepository.findNonAdultFreeNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
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
