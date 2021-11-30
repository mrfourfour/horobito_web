package myproject.demo.Preference.domain.TotalPreferenceCount;

import myproject.demo.Preference.domain.PreferenceCount.PreferenceCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TotalPreferenceCountRepository extends JpaRepository<TotalPreferenceCount, Long> {
    List<TotalPreferenceCount> findTop20ByDeletedOrderByTotalCountDesc(boolean deleted);



    List<TotalPreferenceCount> findTop20ByDeletedAndNovelIdInOrderByTotalCountDesc(boolean deleted, List<Long> novelIds);

}
