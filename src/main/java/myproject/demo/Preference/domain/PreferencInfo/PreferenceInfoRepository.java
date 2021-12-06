package myproject.demo.Preference.domain.PreferencInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PreferenceInfoRepository extends JpaRepository<PreferenceInfo, PreferenceInfoId> {

    List<PreferenceInfo> findAllByDeletedAndPreferenceTimeGreaterThanEqual(boolean b, LocalDateTime atStartOfDay);

    List<PreferenceInfo> findByNovelIdAndDeletedAndPreferenceTimeGreaterThanEqual(
            Long selectedId, boolean deleted,  LocalDateTime atStartOfDay);

    List<PreferenceInfo> findAllByNovelIdInAndDeletedAndPreferenceTimeGreaterThanEqual(List<Long> adultNovelIds, boolean b, LocalDateTime startDay);
}
