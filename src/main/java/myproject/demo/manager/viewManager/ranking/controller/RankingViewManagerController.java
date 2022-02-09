package myproject.demo.manager.viewManager.ranking.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.home.service.HomeViewManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingViewManagerController {

    private final HomeViewManagerService homeViewManagerService;

    @GetMapping("/all/{categoryName}")
    public List<NovelInfoDto> getAllRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return homeViewManagerService.getTopTwentyAll();
        } else {
            return homeViewManagerService.getTopTwentyAllByCategory(categoryName);
        }
    }

    @GetMapping("/adult/{categoryName}")
    public List<NovelInfoDto> getAdultRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return homeViewManagerService.getTopTwentyAdult();
        } else {
            return homeViewManagerService.getTopTwentyAdultByCategory(categoryName);
        }
    }

    @GetMapping("/non-adult/{categoryName}")
    public List<NovelInfoDto> getNonAdultRanking(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            return homeViewManagerService.getTopTwentyNonAdult();
        } else {
            return homeViewManagerService.getTopTwentyNonAdultByCategory(categoryName);
        }
    }
}
