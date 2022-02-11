package myproject.demo.manager.viewManager.novelDetails.controller;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import myproject.demo.manager.viewManager.novelDetails.service.NovelDetailsViewManagerService;
import myproject.demo.manager.viewManager.novelDetails.service.NovelDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novels/")
@RequiredArgsConstructor
public class NovelDetailsViewManagerController {

    private final NovelDetailsViewManagerService novelDetailsViewManagerService;

    @GetMapping("/{novelId}/details")
    public ResponseEntity<NovelDetailsDto> getNovelDetails(@PathVariable Long novelId){
        return ResponseEntity.ok(novelDetailsViewManagerService.getNovelDetails(novelId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }


}
