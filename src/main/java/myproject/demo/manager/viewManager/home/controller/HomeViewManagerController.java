package myproject.demo.manager.viewManager.home.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.home.service.HomeViewManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main/top")
@RequiredArgsConstructor
public class HomeViewManagerController {

    private final HomeViewManagerService homeViewManagerService;

    @GetMapping("/{age}")
    public List<NovelInfoDto> getTopTwenty(@PathVariable String age){
        if (age.equals("all")){
            return homeViewManagerService.getTopTwentyAll();
        }else if(age.equals("adult")){
            return homeViewManagerService.getTopTwentyAdult();
        }else {
            return homeViewManagerService.getTopTwentyNonAdult();
        }
    }

    @GetMapping("/{age}/{categoryName}")
    public List<NovelInfoDto> getTopTwentyByCategory(@PathVariable String age, @PathVariable String categoryName){
        if (age.equals("all")){
            return homeViewManagerService.getTopTwentyAllByCategory(categoryName);
        }else if(age.equals("adult")){
            return homeViewManagerService.getTopTwentyAdultByCategory(categoryName);
        }else {
            return homeViewManagerService.getTopTwentyNonAdultByCategory(categoryName);
        }
    }
}
