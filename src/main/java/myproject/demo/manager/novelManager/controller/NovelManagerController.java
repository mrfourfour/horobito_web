package myproject.demo.manager.novelManager.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/novels")
@RequiredArgsConstructor
public class NovelManagerController {
    private final NovelManagerService novelManagerService;

    @PostMapping
    public ResponseEntity<NovelInfoDto> createNovel(@RequestBody NovelParameter novelParameter) {
        return ResponseEntity.ok(novelManagerService.creatNovel(
                novelParameter.getTitle(),
                novelParameter.getDescription(),
                novelParameter.getCategories(),
                novelParameter.getAge(),
                novelParameter.isPremium(),
                novelParameter.getCoverImageUrl()));
    }

    @PutMapping("/{novelId}")
    public ResponseEntity<NovelInfoDto> updateNovel(@PathVariable Long novelId, @RequestBody NovelParameter novelParameter) throws IllegalAccessException {
        return ResponseEntity.ok(novelManagerService.updateNovelInfo(
                novelId,
                novelParameter.getTitle(),
                novelParameter.getDescription(),
                novelParameter.getCategories(),
                novelParameter.getAge(),
                novelParameter.isPremium(),
                novelParameter.getCoverImageUrl()
        ));
    }

    @DeleteMapping("/{novelId}")
    public ResponseEntity<NovelInfoDto> deleteNovel(@PathVariable Long novelId) throws IllegalAccessException {
        return ResponseEntity.ok(
                novelManagerService.delete(novelId)
        );
    }

    @PutMapping("/{novelId}/undelete")
    public ResponseEntity<NovelInfoDto> resurrect(@PathVariable Long novelId) throws IllegalAccessException {
        return ResponseEntity.ok(
                novelManagerService.resurrect(novelId)
        );
    }

    @GetMapping("/title-duplicate-check/{title}")
    public ResponseEntity<Void> checkTitleDuplicate(@PathVariable String title) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{novelId}")
    public ResponseEntity<NovelInfoDto> viewNovelDetails(@PathVariable Long novelId) {
        return ResponseEntity.ok(novelManagerService.viewNovel(novelId));
    }

}
