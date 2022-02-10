package myproject.demo.manager.viewManager.all.controller;

import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.all.service.AllNovelViewManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/novels")
@RequiredArgsConstructor
public class AllNovelViewManagerController {

    private final AllNovelViewManagerService allNovelViewManagerService;



    @GetMapping("/all")
    public ResponseEntity<List<NovelInfoDto> >getAll(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(allNovelViewManagerService.getAllNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(allNovelViewManagerService.getAllNovelListByCategory(categoryName, sort, cursor, size));
        }
    }

    @GetMapping("/adult")
    public ResponseEntity<List<NovelInfoDto>> getAdult(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(allNovelViewManagerService.getAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(allNovelViewManagerService.getAdultNovelListByCategory(categoryName, sort, cursor, size));

        }
    }

    @GetMapping("/non-adult")
    public ResponseEntity<List<NovelInfoDto>> getNonAdult(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(allNovelViewManagerService.getNonAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(allNovelViewManagerService.getNonAdultNovelListByCategory(categoryName, sort, cursor, size));
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(){
        return ResponseEntity.badRequest().build();
    }
}
