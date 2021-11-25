package myproject.demo.Episode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@NoArgsConstructor
public class EpisodeId implements Serializable {

    private Long novelId;

    private int episodeNum;

    private EpisodeId(Long novelId, int episodeNum) {
        this.novelId = novelId;
        this.episodeNum = episodeNum;
    }

    public static EpisodeId create(Long novelId, int episodeNum){
        return new EpisodeId(novelId, episodeNum);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }

        if (!(obj instanceof EpisodeId)) {
            return false;
        }


        EpisodeId that = (EpisodeId) obj;

        if (this.novelId.equals(that.novelId) && this.episodeNum==that.episodeNum) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(novelId, episodeNum);
    }
}
