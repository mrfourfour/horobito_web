package myproject.demo.view.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewCountRepository extends JpaRepository<ViewCount, ViewCountId> {


    List<ViewCount> findAllByNovelId(Long novelId);

    Optional<ViewCount> findByNovelIdAndEpisodeId(Long novelId, Long episodeId);
}
