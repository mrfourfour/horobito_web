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

    private PreferenceCount(Long novelId, Long episodeId ) {
        this.novelId = novelId;
        this.episodeId = episodeId;
        this.count = Count.create(0L);
    }



}
