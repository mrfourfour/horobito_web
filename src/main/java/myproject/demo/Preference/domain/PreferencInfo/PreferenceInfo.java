package myproject.demo.Preference.domain.PreferencInfo;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(PreferenceInfoId.class)
@NoArgsConstructor
public class PreferenceInfo {

    @Id
    private Long novelId;

    @Id
    private Long userId;

    @Id
    private Long episodeId;


    private boolean deleted;

    private PreferenceInfo(Long novelId, Long userId, Long episodeId) {
        this.novelId = novelId;
        this.userId = userId;
        this.deleted = false;
    }

    public static PreferenceInfo create(Long novelId, Long userId, Long episodeId) {
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

    public Long getEpisodeId() {
        return this.episodeId;
    }


}
