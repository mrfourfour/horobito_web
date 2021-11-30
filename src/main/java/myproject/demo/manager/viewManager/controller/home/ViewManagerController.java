package myproject.demo.manager.viewManager.controller.home;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.service.HomeViewManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main/top")
@RequiredArgsConstructor
public class ViewManagerController {

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
}
