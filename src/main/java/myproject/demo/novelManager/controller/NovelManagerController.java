package myproject.demo.novelManager.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.novelManager.service.NovelInfoDto;
import myproject.demo.novelManager.service.NovelManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/novels")
@RequiredArgsConstructor
public class NovelManagerController {
    private final NovelManagerService novelManagerService;

    @PostMapping("/create")
    public ResponseEntity<NovelInfoDto> createNovel(@RequestBody NovelParameter novelParameter){
        return ResponseEntity.ok(novelManagerService.creatNovel(
                novelParameter.getTitle(),
                novelParameter.getDescription(),
                novelParameter.getCategories(),
                novelParameter.getAge(),
                novelParameter.isPremium(),
                novelParameter.getCoverImageUrl()));
    }
}
