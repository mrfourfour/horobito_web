package myproject.demo.manager.viewManager.home.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.viewManager.home.service.HomeViewManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main/top")
@RequiredArgsConstructor
public class HomeViewManagerController {

    private final HomeViewManagerService homeViewManagerService;

    @GetMapping("/{age}")
    public ResponseEntity<List<NovelInfoDto>> getTopTwenty(@PathVariable String age){
        if (age.equals("all")){
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAll());
        }else if(age.equals("adult")){
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAdult());
        }else {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyNonAdult());
        }
    }

    @GetMapping("/{age}/{categoryName}")
    public ResponseEntity<List<NovelInfoDto>> getTopTwentyByCategory(@PathVariable String age, @PathVariable String categoryName){
        if (age.equals("all")){
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAllByCategory(categoryName));
        }else if(age.equals("adult")){
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyAdultByCategory(categoryName));
        }else {
            return ResponseEntity.ok(homeViewManagerService.getTopTwentyNonAdultByCategory(categoryName));
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(){
        return ResponseEntity.badRequest().build();
    }
}
