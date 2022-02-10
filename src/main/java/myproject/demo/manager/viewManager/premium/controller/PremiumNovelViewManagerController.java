package myproject.demo.manager.viewManager.premium.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.premium.service.PremiumNovelViewManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("novels/premium")
@RequiredArgsConstructor
public class PremiumNovelViewManagerController {

    private final PremiumNovelViewManagerService premiumNovelViewManagerService;

    @GetMapping("/all")
    public ResponseEntity<List<NovelInfoDto>> getAll(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(premiumNovelViewManagerService.getAllNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(premiumNovelViewManagerService.getAllNovelListByCategory(categoryName, sort, cursor, size));
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
            return ResponseEntity.ok(premiumNovelViewManagerService.getAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(premiumNovelViewManagerService.getAdultNovelListByCategory(categoryName, sort, cursor, size));

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
            return ResponseEntity.ok(premiumNovelViewManagerService.getNonAdultNovelList(sort, cursor, size));
        } else {
            return ResponseEntity.ok(premiumNovelViewManagerService.getNonAdultNovelListByCategory(categoryName, sort, cursor, size));
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }
}
