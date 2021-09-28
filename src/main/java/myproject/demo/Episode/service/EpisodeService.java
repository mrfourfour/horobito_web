package myproject.demo.Episode.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.domain.ContentURL;
import myproject.demo.Episode.domain.Episode;
import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final NovelService novelService;

    private final UserService userService;

    private final EpisodeRepository episodeRepository;

    public void create(Long novelId, String content){
        novelService.checkExistenceById(novelId);
        Episode newEpi = Episode.create(
          novelId,
          getEpisodeNum(novelId),
          ContentURL.create(enrollContent(content))
        );
        episodeRepository.save(newEpi);

    }

    private String enrollContent(String content) {

        return "";
    }



    private Long getEpisodeNum(Long novelId) {
        if (!episodeRepository.existsByNovelIdAndDeleted(novelId, false)){
            return 1L;
        }else {
            return episodeRepository.findTopByNovelIdAndDeleted(novelId, false).get().getEpisodeNum()+1;
        }
    }
}
