package myproject.demo.Episode.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@IdClass(EpisodeId.class)
@NoArgsConstructor
public class Episode {

    @Id
    private Long novelId;

    @Id
    private Long episodeNum;

    private boolean deleted;

    private Episode(Long novelId, Long episodeNum) {
        this.novelId = novelId;
        this.episodeNum = episodeNum;
    }

    public static Episode create(Long novelId, Long episodeNum){
        return new Episode(novelId, episodeNum);
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


}
