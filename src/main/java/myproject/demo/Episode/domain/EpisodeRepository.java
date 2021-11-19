package myproject.demo.Episode.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, EpisodeId> {

    boolean existsByNovelIdAndDeleted(Long novelId, boolean deleted);

    Optional<Episode> findTopByNovelIdAndDeleted(Long novelId, boolean deleted);

    List<Episode> findAllByNovelIdAndDeleted(Long novelId, boolean deleted);
}
