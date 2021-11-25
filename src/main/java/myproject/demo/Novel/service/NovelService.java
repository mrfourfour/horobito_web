package myproject.demo.Novel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.*;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final UserService userService;
    private final NovelRepository novelRepository;

    @Transactional
    public NovelDto createNovel(String title, String description, int age, String url) {
        checkDuplicateTitle(title);
        UserDto userDto = userService.findLoggedUser();
        Novel novel = Novel.create(
                Title.create(title),
                Description.create(description),
                AuthorId.create(userDto.getUserId()),
                Age.create(age),
                CoverImageUrl.create(url)
        );

        return createNovelDto(save(novel));
    }

    @Transactional
    public NovelDto editNovel(Long novelId, String title, String description, int age, String url) throws IllegalAccessException {
        checkExistenceById(novelId);
        Novel novel = novelRepository.findById(novelId).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        novel.change(title, description, age, url);
        return createNovelDto(novel);
    }

    @Transactional
    public void resurrect(Long novelId) {
        checkJustExistenceById(novelId);
        Novel novel = novelRepository.findById(novelId).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        novel.resurrect();
    }

    private void checkJustExistenceById(Long novelId) {
        if (!novelRepository.existsById(novelId)) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void delete(Long novelId) throws IllegalAccessException {
        checkExistenceById(novelId);
        Novel novel = novelRepository.findById(novelId).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        novel.delete();
    }

    public List<NovelDto> getNovelsByNovelIds(List<Long> novelIds) {
        List<NovelDto> novels = novelIds.stream()
                .map(id->novelRepository.findByIdAndDeleted(id, false))
                .filter(Optional::isPresent)
                .map(novel -> createNovelDto(novel.get())).collect(Collectors.toList());
        return novels;
    }

    public List<NovelDto> getNovelsByAge(List<Long> novelIds, int age) {
        List<NovelDto> novels = novelIds.stream()
                .map(id->novelRepository.findByIdAndDeletedAndAgeGreaterThanEqual(id, false, Age.create(age)))
                .filter(Optional::isPresent)
                .map(novel -> createNovelDto(novel.get())).collect(Collectors.toList());
        return novels;
    }






    private void checkAlreadyDeleted(Novel novel) {
        if (novel.checkDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    public void checkRequesterIdentity(Long userId, Long authorId){
        if (!userId.equals(authorId)) {
            throw new IllegalArgumentException();
        }
    }

    public void checkExistenceById(Long novelId) {
        if (!novelRepository.existsById(novelId)) {
            throw new IllegalArgumentException();
        }
        if (novelRepository.findById(novelId).get().isDeleted()){
            throw new IllegalArgumentException();
        }
    }


    public void checkExistenceByTitle(String title) {
        if (!novelRepository.existsByTitle(Title.create(title))) {
            throw new IllegalArgumentException();
        }
    }


    private void checkDuplicateTitle(String title) {
        if (novelRepository.existsByTitle(Title.create(title))) {
            throw new DuplicateNovelException();
        }
    }

    private NovelDto createNovelDto(Novel novel) {
        return new NovelDto(
                novel.getId(),
                novel.getAuthorId(),
                novel.getTitle(),
                novel.getDescription(),
                userService.findUserByUserId(novel.getAuthorId()).getUsername(),
                novel.getCoverImageUrl(),
                novel.getAge(),
                novel.isDeleted()

        );
    }

    private Novel save(Novel novel) {
        return novelRepository.saveAndFlush(novel);
    }

    public NovelDto getNovelDto(Long novelId) {
        checkJustExistenceById(novelId);
        return createNovelDto(novelRepository.findById(novelId).get());
    }
}
