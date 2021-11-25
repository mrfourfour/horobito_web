package myproject.demo.manager.episodeManager.controller;


import lombok.RequiredArgsConstructor;
import myproject.demo.manager.episodeManager.service.EpisodeInfoDto;
import myproject.demo.manager.episodeManager.service.EpisodeManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/episodes")
@RequiredArgsConstructor
public class EpisodeManagerController {

    private final EpisodeManagerService episodeManagerService;

    @PostMapping("/{novelId}")
    public ResponseEntity<EpisodeInfoDto> createEpisode(
            @PathVariable Long novelId,
            @RequestBody EpisodeParameter episodeParameter) throws IllegalAccessException {
        return ResponseEntity.ok(
                episodeManagerService.createEpisode(
                        novelId,
                        episodeParameter.getTitle(),
                        episodeParameter.getContent(),
                        episodeParameter.getAuthorComment(),
                        episodeParameter.getAge()
                )
        );
    }

    @PutMapping("/{novelId}/{episodeNum}")
    public ResponseEntity<EpisodeInfoDto> updateEpisode(
            @PathVariable Long novelId,
            @PathVariable int episodeNum,
            @RequestBody EpisodeParameter episodeParameter) throws IllegalAccessException {
        return ResponseEntity.ok(
                episodeManagerService.updateEpisode(
                        novelId,
                        episodeNum,
                        episodeParameter.getTitle(),
                        episodeParameter.getContent(),
                        episodeParameter.getAuthorComment(),
                        episodeParameter.getAge()
                )
        );
    }

    @DeleteMapping("/{novelId}/{episodeNum}")
    public ResponseEntity<EpisodeInfoDto> deleteEpisode(
            @PathVariable Long novelId, @PathVariable int episodeNum) throws IllegalAccessException {
        return ResponseEntity.ok(
                episodeManagerService.deleteEpisode(
                        novelId,
                        episodeNum
                )
        );
    }

    @PutMapping("/{novelId}/undelete/{episodeId}")
    public ResponseEntity<EpisodeInfoDto> resurrectEpisode(
            @PathVariable Long novelId, @PathVariable int episodeId) throws IllegalAccessException {
        return ResponseEntity.ok(
                episodeManagerService.resurrect(
                        novelId,
                        episodeId
                )
        );
    }

}
