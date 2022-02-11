package myproject.demo.manager.bookmarkManager.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookMarkManagerService {

    private final BookMarkService bookMarkService;
    private final NovelViewModelService novelViewModelService;
    private final NovelService novelService;



    @Transactional
    public void setBookMark(Long novelId) {
        novelService.checkExistenceById(novelId);
        bookMarkService.create(novelId);
        novelViewModelService.increaseBookmarkCount(novelId);
    }

    @Transactional
    public void unSetBookMark(Long novelId) {
        novelService.checkExistenceById(novelId);
        bookMarkService.delete(novelId);
        novelViewModelService.decreaseBookmarkCount(novelId);
    }
}
