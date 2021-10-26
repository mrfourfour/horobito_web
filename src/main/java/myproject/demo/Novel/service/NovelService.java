package myproject.demo.Novel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.*;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final UserService userService;
    private final NovelRepository novelRepository;

    @Transactional
    public NovelDto createNovel(String title, String description, int age) {
        checkDuplicateTitle(title);
        UserDto userDto = userService.findLoggedUser();
        Novel novel = Novel.create(
                Title.create(title),
                Description.create(description),
                AuthorId.create(userDto.getUserId()),
                Age.create(age)
        );
        save(novel);
        return createNovelDto(novel);
    }

    @Transactional
    public NovelDto editNovel(Long novelId, String title, String description, int age) throws IllegalAccessException {
        checkExistenceById(novelId);
        Novel novel = novelRepository.findByTitle(Title.create(title)).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        novel.change(title, description, age);
        return createNovelDto(novel);
    }

    @Transactional
    public void delete(Long novelId) throws IllegalAccessException {
        checkExistenceById(novelId);
        Novel novel = novelRepository.findById(novelId).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        checkAlreadyDeleted(novel);
        novel.delete();
    }



    private List<NovelDto> getAllNovels(List<Long> novelIds) {
        List<NovelDto> novels = novelIds.stream()
                .map(id->novelRepository.findByIdAndDeleted(id, false))
                .map(novel -> createNovelDto(novel.get())).collect(Collectors.toList());
        return novels;
    }

    private List<NovelDto> getNovelsByAge(List<Long> novelIds, int age) {
        List<NovelDto> novels = novelIds.stream()
                .map(id->novelRepository.findByIdAndDeletedAndAgeGreaterThanEqual(id, false, Age.create(age)))
                .map(novel -> createNovelDto(novel.get())).collect(Collectors.toList());
        return novels;
    }






    private void checkAlreadyDeleted(Novel novel) {
        if (novel.checkDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkRequesterIdentity(Long userId, Long authorId) throws IllegalAccessException {
        if (!userId.equals(authorId)) {
            throw new IllegalAccessException();
        }
    }

    public void checkExistenceById(Long novelId) {
        if (!novelRepository.existsById(novelId)) {
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
                novel.getTitle(),
                novel.getDescription(),
                userService.findUserByUserId(novel.getAuthorId()).getUsername()
        );
    }

    private void save(Novel novel) {
        novelRepository.save(novel);
    }
}
