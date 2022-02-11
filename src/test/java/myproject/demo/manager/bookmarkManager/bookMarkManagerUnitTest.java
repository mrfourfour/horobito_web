package myproject.demo.manager.bookmarkManager;


import myproject.demo.Novel.service.NovelService;
import myproject.demo.bookmark.service.BookMarkService;
import myproject.demo.manager.bookmarkManager.service.BookMarkManagerService;
import myproject.demo.novelViewModel.service.NovelViewModelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class bookMarkManagerUnitTest {

    @Mock
    BookMarkService bookMarkService;
    @Mock
    NovelViewModelService novelViewModelService;
    @Mock
    NovelService novelService;


    @Test
    @DisplayName("normal condition")
    public void test1(){
        BookMarkManagerService sut = new BookMarkManagerService(
                bookMarkService, novelViewModelService, novelService
        );
        Long novelId = 2L;

        sut.setBookMark(novelId);

        verify(novelService, times(1)).checkExistenceById(any());
        verify(bookMarkService, times(1)).create(any());
        verify(novelViewModelService, times(1)).increaseBookmarkCount(any());
    }
}
