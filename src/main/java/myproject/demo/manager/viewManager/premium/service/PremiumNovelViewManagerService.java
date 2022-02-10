package myproject.demo.manager.viewManager.premium.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.category.service.CategoryService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import myproject.demo.manager.viewManager.premium.domain.PremiumNovelViewModelRepository;
import myproject.demo.novelViewModel.domain.NovelViewModel;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PremiumNovelViewManagerService {

    private final NovelViewModelService novelViewModelService;
    private final NovelManagerService novelManagerService;
    private final PremiumNovelViewModelRepository premiumNovelViewModelRepository;
    private final CategoryService categoryService;

    public List<NovelInfoDto> getAllNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }


    public List<NovelInfoDto> getAllNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAllPremiumNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultPremiumNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultPremiumNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultPremiumNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultPremiumNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultPremiumNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelList(String sort, Long cursor, int size) {
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultPremiumNovelInViewOrder(cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultPremiumNovelInPreferenceOrder(cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultPremiumNovelInDateOrder(cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultPremiumNovelInEpisodeCountOrder(cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultPremiumNovelInBookMarkCountOrder(cursor, size));
        }else {
            throw new IllegalArgumentException();
        }
    }

    public List<NovelInfoDto> getNonAdultNovelListByCategory(String categoryName, String sort, Long cursor, int size) {
        Long categoryId = getCategoryIdByName(categoryName);
        if (sort.equals("view")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultNovelInViewOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("preference")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultNovelInPreferenceOrderWithCategory(categoryId, cursor, size));
        }else if(sort.equals("date")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultNovelInDateOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("episode")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultNovelInEpisodeCountOrderWithCategory(categoryId, cursor, size));
        }else if (sort.equals("book-mark")){
            return getNovelInfoList(premiumNovelViewModelRepository.findNonAdultNovelInBookMarkCountOrderWithCategory(categoryId, cursor, size));
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
