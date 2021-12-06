package myproject.demo.Preference.domain.PreferencInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(PreferenceInfoId.class)
@NoArgsConstructor
@Getter
public class PreferenceInfo {

    @Id
    private Long novelId;

    @Id
    private Long userId;

    @Id
    private int episodeId;

    private LocalDateTime preferenceTime;


    private boolean deleted;

    private PreferenceInfo(Long novelId, Long userId, int episodeId) {
        this.novelId = novelId;
        this.userId = userId;
        this.episodeId = episodeId;
        this.deleted = false;
        this.preferenceTime = LocalDateTime.now();
    }

    public static PreferenceInfo create(Long novelId, Long userId, int episodeId) {
        return new PreferenceInfo(novelId, userId, episodeId);
    }

    public PreferenceInfoId getId() {
        return PreferenceInfoId.create(this.novelId, this.userId, episodeId);
    }

    public boolean checkDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public void resurrect(){
        this.deleted = false;
    }

    public Long getNovelId() {
        return this.novelId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public int getEpisodeId() {
        return this.episodeId;
    }

    public void renewalPreferenceTime(){
        this.preferenceTime = LocalDateTime.now();
    }



}
