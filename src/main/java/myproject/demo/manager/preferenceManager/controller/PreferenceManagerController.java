package myproject.demo.manager.preferenceManager.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.preferenceManager.service.PreferenceManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class PreferenceManagerController {

    private final PreferenceManagerService preferenceManagerService;

    @PostMapping("/{novelId}/{episodeId}")
    public ResponseEntity<Void> prefer(@PathVariable Long novelId, @PathVariable int episodeId) {
        preferenceManagerService.preferEpisode(novelId, episodeId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{novelId}/{episodeId}")
    public ResponseEntity<Void> cancelPreference(@PathVariable Long novelId, @PathVariable int episodeId) {
        preferenceManagerService.cancelPreference(novelId, episodeId);
        return ResponseEntity.ok().build();
    }
}
