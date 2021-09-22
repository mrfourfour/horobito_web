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
    public NovelDto createNovel(String title, String description) {
        checkDuplicateTitle(title);
        UserDto userDto = userService.findLoggedUser();
        Novel novel = Novel.create(
                Title.create(title),
                Description.create(description),
                AuthorId.create(userDto.getUserId())
        );
        save(novel);
        return createNovelDto(novel);
    }

    @Transactional
    public NovelDto editNovel(Long novelId, String title, String description) throws IllegalAccessException {
        checkExistenceById(novelId);
        Novel novel = novelRepository.findByTitle(Title.create(title)).get();
        checkRequesterIdentity(userService.findLoggedUser().getUserId(), novel.getAuthorId());
        novel.change(title, description);
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

    private List<NovelDto> getNovels(List<Long> novelIds) {
        List<NovelDto> novels = novelIds.stream()
                .map(novelRepository::findById)
                .filter(novel -> !novel.get().checkDeleted())
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

    private void checkExistenceById(Long novelId) {
        if (!novelRepository.existsById(novelId)) {
            throw new IllegalArgumentException();
        }
    }


    private void checkExistenceByTitle(String title) {
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
