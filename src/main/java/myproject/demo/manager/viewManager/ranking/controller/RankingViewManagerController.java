package myproject.demo.manager.viewManager.ranking.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.home.service.HomeViewManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingViewManagerController {

    private final HomeViewManagerService homeViewManagerService;

    @GetMapping("/all/{categoryName}")
    public ResponseEntity<List<NovelInfoDto>> getAllRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAll());
        } else {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAllByCategory(categoryName));
        }
    }

    @GetMapping("/adult/{categoryName}")
    public ResponseEntity<List<NovelInfoDto>> getAdultRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAdult());
        } else {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAdultByCategory(categoryName));
        }
    }

    @GetMapping("/non-adult/{categoryName}")
    public ResponseEntity<List<NovelInfoDto>> getNonAdultRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyNonAdult());
        } else {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyNonAdultByCategory(categoryName));
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(){
        return ResponseEntity.badRequest().build();
    }

}
