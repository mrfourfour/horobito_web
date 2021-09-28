package myproject.demo.Preference.domain.PreferenceCount;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class PreferenceCount {

    @Id
    private Long novelId;

    @Id
    private Long episodeId;

    @Embedded
    private Count count;

    private PreferenceCount(Long novelId, Long episodeId, Long count ) {
        this.novelId = novelId;
        this.episodeId = episodeId;
        this.count = Count.create(count);
    }

    public static PreferenceCount create(Long novelId, Long episodeId, Long count ){
        return new PreferenceCount(novelId, episodeId, count);
    }

    public Long getEpisodeId() {
        return this.episodeId;
    }

    public Long getNovelId() {
        return this.novelId;
    }



}
