package myproject.demo.Preference.domain.PreferenceCount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceCountRepository extends JpaRepository<PreferenceCount, PreferenceCountId> {

    List<PreferenceCount> findAllByNovelId(Long novelId);

    List<PreferenceCount> findAllByNovelIdAndEpisodeIdIn(Long novelId, List<Integer> episodeIDs);
}
