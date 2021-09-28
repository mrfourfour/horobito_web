package myproject.demo.Episode.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.domain.*;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final NovelService novelService;

    private final UserService userService;

    private final EpisodeRepository episodeRepository;

    public void create(Long novelId, String content, String authorComment ){
        novelService.checkExistenceById(novelId);
        Episode newEpi = Episode.create(
          novelId,
          getEpisodeNum(novelId),
          AuthorComment.create(authorComment),
          ContentURL.create(enrollContent(content))
        );
        episodeRepository.save(newEpi);

    }

    private String enrollContent(String content) {

        return "";
    }

    private void changeContent(Long novelId, Long episodeNum, String content){
        novelService.checkExistenceById(novelId);
        checkExistence(novelId, episodeNum);
        editContent(episodeNum, content);
    }

    private void editContent(Long episodeNum, String content) {
    }

    private void checkExistence(Long novelId, Long episodeNum) {
        if (!(episodeRepository.existsById(EpisodeId.create(novelId, episodeNum)))){
            throw new IllegalArgumentException();
        }
    }

    private Long getEpisodeNum(Long novelId) {
        if (!episodeRepository.existsByNovelIdAndDeleted(novelId, false)){
            return 1L;
        }else {
            return episodeRepository.findTopByNovelIdAndDeleted(novelId, false).get().getEpisodeNum()+1;
        }
    }
}
