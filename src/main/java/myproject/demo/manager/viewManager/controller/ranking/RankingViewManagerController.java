package myproject.demo.manager.viewManager.controller.ranking;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.service.RankingViewManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingViewManagerController {

    private final RankingViewManagerService rankingViewManagerService;

    @GetMapping("/{age}/{categoryName}")
    public List<NovelInfoDto> getRankingByAgeAndCategory(
            @PathVariable String age,
            @PathVariable String categoryName,
            @RequestParam(value = "from") String date,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ){
        if (age.equals("all")){
            return rankingViewManagerService.getTopAllByCategory(categoryName, date, cursor, size);
        }else if(age.equals("adult")){
            return rankingViewManagerService.getTopAdultByCategory(categoryName, date, cursor, size);
        }else {
            return rankingViewManagerService.getTopNonAdultByCategory(categoryName, date, cursor, size);
        }
    }
}
