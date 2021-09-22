package myproject.demo.Novel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.*;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final UserService userService;
    private final NovelRepository novelRepository;

    public NovelDto createNovel(String title, String description){
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

    public NovelDto editNovel(Long novelId, String title, String description){
        checkExistenceById(novelId);
        Novel novel = novelRepository.findByTitle(Title.create(title)).get();
        novel.change(title, description);
        return createNovelDto(novel);
    }

    private void checkExistenceById(Long novelId) {
        if(!novelRepository.existsById(novelId)){
            throw new IllegalArgumentException();
        }
    }



    private void checkExistenceByTitle(String title) {
        if (!novelRepository.existsByTitle(Title.create(title))){
            throw new IllegalArgumentException();
        }
    }


    private void checkDuplicateTitle(String title) {
        if (novelRepository.existsByTitle(Title.create(title))){
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
