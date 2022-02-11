package myproject.demo.Episode.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.domain.*;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final NovelService novelService;

    private final UserService userService;

    private final EpisodeRepository episodeRepository;

    @Transactional
    public EpisodeDto create(Long novelId, String title, String content, String authorComment, int age){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        Episode newEpi = Episode.create(
                novelId,
                getEpisodeNum(novelId),
                title,
                AuthorComment.create(authorComment),
                ContentURL.create(enrollContent(content)),
                age
        );
        return getEpisodeDto(episodeRepository.saveAndFlush(newEpi));

    }

    private EpisodeDto getEpisodeDto(Episode episode) {
        return new EpisodeDto(
                episode.getNovelId(),
                episode.getEpisodeNum(),
                episode.getTitle(),
                episode.getURL(),
                episode.getAuthorComment(),
                episode.getRegistrationTime(),
                episode.getAge(),
                episode.checkDeleted()
        );
    }

    @Transactional
    public void delete(Long novelId, int episodeNum) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).ifPresent(
                selectedEpisode->{
                    checkAlreadyDeleted(selectedEpisode);
                    selectedEpisode.delete();
                });

    }

    @Transactional
    public void changeAuthorComment(Long novelId, int episodeNum, String newComment) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        novelService.checkRequesterIdentity (
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).ifPresent(
                selectedEpisode->{
                    checkAlreadyDeleted(selectedEpisode);
                    selectedEpisode.changeAuthorComment(newComment);
                });
    }


    @Transactional
    public void changeContent(Long novelId, int episodeNum, String content) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        novelService.checkRequesterIdentity (
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).ifPresent(
                selectedEpisode->{
                    checkAlreadyDeleted(selectedEpisode);
                    selectedEpisode.changeContent(editContent(episodeNum, content));
                });

    }

    @Transactional
    public void changeAge(Long novelId, int episodeNum, int age) {
        novelService.checkExistenceById(novelId);
        checkExistenceById(novelId, episodeNum);
        novelService.checkRequesterIdentity (
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).ifPresent(
                selectedEpisode->{
                    checkAlreadyDeleted(selectedEpisode);
                    selectedEpisode.changeAge(age);
                });

    }

    @Transactional
    public void resurrect(Long novelId, int episodeNum){
        novelService.checkExistenceById(novelId);
        checkJustExistenceById(novelId, episodeNum);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).ifPresent(
                Episode::resurrect);

    }

    private void checkJustExistenceById(Long novelId, int episodeNum) {
        if (!(episodeRepository.existsById(EpisodeId.create(novelId, episodeNum)))) {
            throw new IllegalArgumentException();
        }
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

    private String editContent(int episodeNum, String content) {
        /*
        change content and get content locker url
         */
        return "tempUrl";
    }

    public void checkExistenceById(Long novelId, int episodeNum) {
        if (!(episodeRepository.existsById(EpisodeId.create(novelId, episodeNum)))
                || episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).get().isDeleted()) {
            throw new IllegalArgumentException();
        }
    }

    private int getEpisodeNum(Long novelId) {
        if (!episodeRepository.existsByNovelIdAndDeleted(novelId, false)) {
            return 1;
        } else {
            List<Episode> list = episodeRepository.findAllByNovelId(novelId);
            return list.get(list.size()-1).getEpisodeNum() + 1;
        }
    }

    public List<EpisodeDto> findAllByNovelId(Long novelId) {
        return episodeRepository.findAllByNovelIdAndDeleted(novelId, false)
                .stream().map(this::getEpisodeDto).collect(Collectors.toList());
    }

    public EpisodeDto getEpisode(Long novelId, int episodeNum) {
        checkJustExistence(novelId, episodeNum);
        return getEpisodeDto(episodeRepository.findById(EpisodeId.create(novelId, episodeNum)).get());
    }

    private void checkJustExistence(Long novelId, int episodeNum) {
        if (!(episodeRepository.existsById(EpisodeId.create(novelId, episodeNum)))) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void update(Long novelId, int episodeId, String title, String content, String authorComment, int age) {
        checkExistenceById(novelId, episodeId);
        episodeRepository.findById(EpisodeId.create(novelId, episodeId)).get().change(
                title,
                authorComment,
                enrollContent(content),
                age
        );

    }
}
