package myproject.demo.view.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.view.domain.ViewCount;
import myproject.demo.view.domain.ViewCountId;
import myproject.demo.view.domain.ViewCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewCountRepository viewCountRepository;
    private final NovelService novelService;
    private final EpisodeService episodeService;

    @Transactional
    public void createViewInfo(Long novelId, int episodeId){
        checkAlreadyExistence(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        viewCountRepository.saveAndFlush(ViewCount.create(novelId, episodeId, 0L));

    }

    @Transactional
    public void delete(Long novelId, int episodeId){
        checkExistence(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        viewCountRepository.findById(ViewCountId.create(novelId, episodeId))
                .ifPresent(ViewCount::delete);
    }

    @Transactional
    public void resurrect(Long novelId, int episodeId){
        checkExistence(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        viewCountRepository.findById(ViewCountId.create(novelId, episodeId))
                .ifPresent(ViewCount::resurrect);
    }


    public Long getTotalViewCount(Long novelId, List<Integer> episodeIds){
        Long count = 0L;
        return viewCountRepository.findAllByNovelIdAndEpisodeIdIn(novelId, episodeIds)
                .stream()
                .map(ViewCount::getCount).reduce(0L, Long::sum);
    }

    private void checkAlreadyExistence(Long novelId, int episodeId) {
        if (viewCountRepository.existsById(ViewCountId.create(novelId, episodeId))
                &&!viewCountRepository.findById(ViewCountId.create(novelId, episodeId)).get().isDeleted()){
            throw new IllegalArgumentException();
        }
    }

    public void increase(Long novelId, int episodeId){
        checkNonExistOrAlreadyDeleted(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        ViewCount viewCount = viewCountRepository.findById(ViewCountId.create(novelId, episodeId)).get();
        viewCount.increase();
    }

    private void checkNonExistOrAlreadyDeleted(Long novelId, int episodeId) {
        if (!viewCountRepository.existsById(ViewCountId.create(novelId, episodeId))
                ||viewCountRepository.findById(ViewCountId.create(novelId, episodeId)).get().isDeleted()){
            throw new IllegalArgumentException();
        }
    }

    private void checkExistence(Long novelId, int episodeId) {
        if (!viewCountRepository.existsById(ViewCountId.create(novelId, episodeId))){
            throw new IllegalArgumentException();
        }
    }
}
