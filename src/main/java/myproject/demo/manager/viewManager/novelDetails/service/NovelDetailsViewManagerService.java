package myproject.demo.manager.viewManager.novelDetails.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeDto;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.novelViewModel.service.NovelViewModelDto;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelDetailsViewManagerService {

    private final NovelService novelService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EpisodeService episodeService;
    private final BookMarkService bookMarkService;
    private final NovelViewModelService novelViewModelService;


    public NovelDetailsDto getNovelDetails(Long novelId) {
        novelService.checkExistenceById(novelId);
        NovelViewModelDto novelViewModelDto = novelViewModelService.getViewModel(novelId);
        List<CategoryDto> categoryDtoList = categoryService.findAllCategoryByNovelId(novelId);
        List<EpisodeDto> episodeDtoList = episodeService.findAllByNovelId(novelId);

        return this.getNovelDetailsDto(
                novelViewModelDto,
                bookMarkService.checkUserAlreadyBookmark(userService.findLoggedUser().getUserId(), novelId),
                categoryDtoList,
                episodeDtoList
        );
    }

    private NovelDetailsDto getNovelDetailsDto(NovelViewModelDto novelViewModelDto, boolean isAlreadyBookmark, List<CategoryDto> categoryDtoList, List<EpisodeDto> episodeDtoList) {
        return new NovelDetailsDto(
                novelViewModelDto.getTitle(),
                novelViewModelDto.getNovelId(),
                novelViewModelDto.isPremium(),
                categoryDtoList,
                novelViewModelDto.getEpisodeNumber(),
                novelViewModelDto.getPreferenceCount(),
                novelViewModelDto.getBookMarkCount(),
                novelViewModelDto.isDeleted(),
                episodeDtoList,
                isAlreadyBookmark
        );
    }


}
