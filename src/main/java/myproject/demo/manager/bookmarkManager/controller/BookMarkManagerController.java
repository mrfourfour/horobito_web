package myproject.demo.manager.bookmarkManager.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.bookmarkManager.service.BookMarkManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-mark")
public class BookMarkManagerController {

    private final BookMarkManagerService bookMarkManagerService;

    @PostMapping("/{novelId}")
    public ResponseEntity<Void> setBookMark(@PathVariable Long novelId){
        bookMarkManagerService.setBookMark(novelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<Void> unSetBookMark(@PathVariable Long novelId){
        bookMarkManagerService.unSetBookMark(novelId);
        return ResponseEntity.ok().build();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }
}
