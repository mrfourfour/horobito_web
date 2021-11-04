package myproject.demo.bookmark.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.User.service.UserService;
import myproject.demo.bookmark.domain.BookMark;
import myproject.demo.bookmark.domain.BookMarkId;
import myproject.demo.bookmark.domain.BookMarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final UserService userService;
    private final BookMarkRepository bookMarkRepository;

    @Transactional
    public void create(Long novelId){
        Long userId = userService.findLoggedUser().getUserId();
        checkExistence(userId, novelId);
        BookMark created = bookMarkRepository.saveAndFlush(BookMark.create(userId, novelId));
    }

    public int getTotalBookMarkCount(Long novel){
        return bookMarkRepository.findAllByNovelIdAndDeleted(novel, false).size();
    }

    private void checkExistence(Long userId, Long novelId) {
        if(bookMarkRepository.existsById(BookMarkId.create(userId, novelId))){
            throw new IllegalArgumentException();
        }
    }
}
