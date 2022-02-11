package myproject.demo.manager.viewManager.free.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.free.service.FreeNovelViewManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("novels/premium")
@RequiredArgsConstructor
public class FreeNovelViewManagerController {

    private final FreeNovelViewManagerService freeNovelViewManagerService;

    @GetMapping("/all")
    public ResponseEntity<List<NovelInfoDto>> getAll(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(freeNovelViewManagerService.getAllNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(freeNovelViewManagerService.getAllNovelListByCategory(categoryName, sort, cursor, size));
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
            return ResponseEntity.ok(freeNovelViewManagerService.getAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(freeNovelViewManagerService.getAdultNovelListByCategory(categoryName, sort, cursor, size));

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
            return ResponseEntity.ok(freeNovelViewManagerService.getNonAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(freeNovelViewManagerService.getNonAdultNovelListByCategory(categoryName, sort, cursor, size));
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }

}
