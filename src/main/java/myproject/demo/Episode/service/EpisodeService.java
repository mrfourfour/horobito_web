package myproject.demo.Episode.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.domain.*;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final NovelService novelService;

    private final UserService userService;

    private final EpisodeRepository episodeRepository;

    @Transactional
    public void create(Long novelId, String content, String authorComment) {
        novelService.checkExistenceById(novelId);
        Episode newEpi = Episode.create(
                novelId,
                getEpisodeNum(novelId),
                AuthorComment.create(authorComment),
                ContentURL.create(enrollContent(content))
        );
        episodeRepository.save(newEpi);

    }

    @Transactional
    public void changeAuthorComment(Long novelId, Long episodeNum, String newComment) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        Episode episode = episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).get();
        checkAlreadyDeleted(episode);
        episode.changeAuthorComment(newComment);
    }

    @Transactional
    public void delete(Long novelId, Long episodeNum) {
        checkExistenceById(novelId, episodeNum);
        Episode episode = episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).get();
        checkAlreadyDeleted(episode);
        episode.delete();
    }


    @Transactional
    public void resurrect(Long novelId, Long episodeNum){
        checkExistenceById(novelId, episodeNum);
        Episode episode = episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).get();
        checkAlreadyDeleted(episode);
        episode.delete();
    }

    public int getTotalEpisode(Long novelId){
        return episodeRepository.findAllByNovelIdAndDeleted(novelId, false).size();
    }

    private void checkAlreadyDeleted(Episode episode) {
        if (episode.isDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private String enrollContent(String content) {

        return "";
    }

    private void changeContent(Long novelId, Long episodeNum, String content) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        editContent(episodeNum, content);
    }

    private void editContent(Long episodeNum, String content) {
    }

    public void checkExistenceById(Long novelId, Long episodeNum) {
        if (!(episodeRepository.existsById(EpisodeId.create(novelId, episodeNum)))) {
            throw new IllegalArgumentException();
        }
    }

    private Long getEpisodeNum(Long novelId) {
        if (!episodeRepository.existsByNovelIdAndDeleted(novelId, false)) {
            return 1L;
        } else {
            return episodeRepository.findTopByNovelIdAndDeleted(novelId, false).get().getEpisodeNum() + 1;
        }
    }
}
