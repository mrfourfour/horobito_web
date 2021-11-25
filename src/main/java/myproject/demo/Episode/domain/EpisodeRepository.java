package myproject.demo.Episode.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, EpisodeId> {

    boolean existsByNovelIdAndDeleted(Long novelId, boolean deleted);


    List<Episode> findAllByNovelId(Long novelId);

    List<Episode> findAllByNovelIdAndDeleted(Long novelId, boolean b);
}
