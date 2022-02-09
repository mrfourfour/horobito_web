package myproject.demo.novelViewModel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.novelViewModel.domain.NovelViewModel;
import myproject.demo.novelViewModel.domain.NovelViewModelRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NovelViewModelService {


    private final PageRequest PAGE_REQUEST = PageRequest.of(0, 20);

    private final NovelViewModelRepository novelViewModelRepository;


    public void create(Long novelId, String title, String authorName, String coverImageUrl, String description, boolean deleted, int age, boolean premium, LocalDateTime updateTime, Long preferenceCount, int bookMarkCount, Long view, int episodeNumber) {
        NovelViewModel model = NovelViewModel.create(novelId, title, authorName, coverImageUrl, description, deleted, age, premium, updateTime, preferenceCount, bookMarkCount, view, episodeNumber);
        novelViewModelRepository.saveAndFlush(model);
    }


    public void updateNovelDomainArea(Long novelId, String title, String authorName, String coverImageUrl, String description, boolean deleted, int age) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updateNovelDomain(title, authorName, coverImageUrl, description, deleted, age);
        });
    }


    public void updatePremium(Long novelId, boolean premium

    ) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updatePremium(premium);
        });
    }


    public void updateTime(Long novelId, LocalDateTime updateTime) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updateTime(updateTime);
        });
    }


    public void updatePreferenceCount(Long novelId, Long preferenceCount) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updatePreferenceCount(preferenceCount);
        });
    }


    public void updateBookMarkCount(Long novelId, int bookMarkCount
    ) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updateBookMarkCount(bookMarkCount);
        });
    }


    public void updateViewCount(Long novelId, Long view) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updateViewCount(view);
        });
    }


    public void updateEpisodeNumber(Long novelId, int episodeNumber) {
        novelViewModelRepository.findById(novelId).ifPresent(model -> {
            model.updateEpisodeNumber(episodeNumber);
        });
    }

    public void prefer(Long novelId) {
        novelViewModelRepository.findById(novelId).ifPresent(NovelViewModel::increasePrefer);
    }

    public void cancelPreference(Long novelId){
        novelViewModelRepository.findById(novelId).ifPresent(NovelViewModel::decreasePrefer);
    }

    public void delete(Long novelId) {
        novelViewModelRepository.findById(novelId).ifPresent(NovelViewModel::delete);
    }

    public void resurrect(Long novelId) {
        novelViewModelRepository.findById(novelId).ifPresent(NovelViewModel::resurrect);
    }


    public List<NovelViewModelDto> getTopTwentyAll() {
        return getDtoList(novelViewModelRepository.findTop20(PAGE_REQUEST));
    }

    public List<NovelViewModelDto> getTopTwentyAdult() {
        return getDtoList(novelViewModelRepository.findTop20Adult(PAGE_REQUEST));
    }

    public List<NovelViewModelDto> getTopTwentyNonAdult() {
        return getDtoList(novelViewModelRepository.findTop20NonAdult(PAGE_REQUEST));
    }

    public List<NovelViewModelDto> getTopTwentyAllByCategory(long categoryId) {
        return getDtoList(novelViewModelRepository.findTop20ByCategory(categoryId, PAGE_REQUEST));
    }

    public List<NovelViewModelDto> getTopTwentyAdultByCategory(long categoryId) {
        return getDtoList(novelViewModelRepository.findTop20AdultByCategory(categoryId, PAGE_REQUEST));
    }

    public List<NovelViewModelDto> getTopTwentyNonAdultByCategory(long categoryId) {
        return getDtoList(novelViewModelRepository.findTop20NonAdultByCategory(categoryId, PAGE_REQUEST));
    }




    public List<NovelViewModelDto> getRankingAllByCategory(LocalDateTime startDay, long categoryId, long cursor, int size) {
        return getDtoList(novelViewModelRepository
                .findRankAllByCategory(startDay, categoryId, cursor, size));
    }

    public List<NovelViewModelDto> getRankingAdultByCategory(LocalDateTime startDay, long categoryId, long cursor, int size) {
        return getDtoList(novelViewModelRepository.findRankAdultByCategory(startDay, categoryId, cursor, size));
    }

    public List<NovelViewModelDto> getRankingNonAdultByCategory(LocalDateTime startDay, long categoryId, long cursor, int size) {
        return getDtoList(novelViewModelRepository.findRankNonAdultByCategory(startDay, categoryId, cursor, size));
    }

    public List<NovelViewModelDto> getRankingAll(LocalDateTime startDay, long cursor, int size) {
        return getDtoList(novelViewModelRepository.findRankAll(startDay, cursor, size));
    }

    public List<NovelViewModelDto> getRankingAdultAll(LocalDateTime startDay, Long cursor, int size) {
        return getDtoList(novelViewModelRepository.findRankAdult(startDay, cursor, size));
    }

    public List<NovelViewModelDto> getRankingNonAdultAll(LocalDateTime startDay, Long cursor, int size) {
        return getDtoList(novelViewModelRepository.findRankNonAdult(startDay, cursor, size));
    }




    public List<NovelViewModelDto> getDtoList(List<NovelViewModel> novelViewModels) {
        return novelViewModels.stream()
                .map(this::getModelDto).collect(Collectors.toList());
    }

    public NovelViewModelDto getViewModel(Long novelId) {
        Optional<NovelViewModel> model = novelViewModelRepository.findById(novelId);
        return getModelDto(model.get());
    }

    private NovelViewModelDto getModelDto(NovelViewModel novelViewModel) {
        return new NovelViewModelDto(novelViewModel.getNovelId(), novelViewModel.getTitle(), novelViewModel.getAuthorName(), novelViewModel.getCoverImageUrl(), novelViewModel.getDescription(), novelViewModel.isDeleted(), novelViewModel.getAge(), novelViewModel.isPremium(), novelViewModel.getUpdateTime(), novelViewModel.getPreferenceCount(), novelViewModel.getBookMarkCount(), novelViewModel.getView(), novelViewModel.getEpisodeNumber());
    }


    public void increaseBookmarkCount(Long novelId) {
        Optional<NovelViewModel> temp = novelViewModelRepository.findById(novelId);
        temp.ifPresent(
                NovelViewModel::increaseBookmarkCount
        );
        novelViewModelRepository.save(temp.get());
    }


    public void decreaseBookmarkCount(Long novelId) {
        Optional<NovelViewModel> temp = novelViewModelRepository.findById(novelId);
        temp.ifPresent(
                NovelViewModel::decreaseBookmarkCount
        );
        novelViewModelRepository.save(temp.get());
    }


    public void increaseEpisodeCount(Long novelId) {
        Optional<NovelViewModel> temp = novelViewModelRepository.findById(novelId);
        temp.ifPresent(
                NovelViewModel::increaseEpisodeCount
        );
        novelViewModelRepository.save(temp.get());
    }


    public void decreaseEpisodeCount(Long novelId) {
        Optional<NovelViewModel> temp = novelViewModelRepository.findById(novelId);
        temp.ifPresent(
                NovelViewModel::decreaseEpisodeCount
        );
        novelViewModelRepository.save(temp.get());
    }


}
