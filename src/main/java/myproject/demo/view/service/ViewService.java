package myproject.demo.view.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.service.EpisodeService;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.view.domain.ViewCount;
import myproject.demo.view.domain.ViewCountId;
import myproject.demo.view.domain.ViewCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewCountRepository viewCountRepository;
    private final NovelService novelService;
    private final EpisodeService episodeService;

    @Transactional
    public void createViewInfo(Long novelId, Long episodeId){
        checkAlreadyExistence(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        viewCountRepository.save(ViewCount.create(novelId,episodeId, 0L));

    }

    public Long getTotalViewCount(Long novelId){
        Long count = 0L;
        return viewCountRepository.findAllByNovelId(novelId)
                .stream()
                .map(ViewCount::getCount).reduce(0L, Long::sum);
    }

    private void checkAlreadyExistence(Long novelId, Long episodeId) {
        if (viewCountRepository.existsById(ViewCountId.create(novelId, episodeId))){
            throw new IllegalArgumentException();
        }
    }

    public void increase(Long novelId, Long episodeId){
        checkExistence(novelId, episodeId);
        novelService.checkExistenceById(novelId);
        episodeService.checkExistenceById(novelId, episodeId);
        ViewCount viewCount = viewCountRepository.findByNovelIdAndEpisodeId(novelId, episodeId).get();
        viewCount.increase();
    }

    private void checkExistence(Long novelId, Long episodeId) {
        if (!viewCountRepository.existsById(ViewCountId.create(novelId, episodeId))){
            throw new IllegalArgumentException();
        }
    }
}
