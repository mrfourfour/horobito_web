package myproject.demo.Episode.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, EpisodeId> {
}
