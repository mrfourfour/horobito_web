package myproject.demo.view.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class ViewCountId implements Serializable {

    private Long novelId;

    private Long episodeId;


    private ViewCountId(Long novelId, Long episodeId) {
        this.novelId = novelId;
        this.episodeId = episodeId;
    }

    public static ViewCountId create(Long novelId, Long episodeId){
        return new ViewCountId(novelId, episodeId);
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

        if (!(obj instanceof ViewCountId)) {
            return false;
        }


        ViewCountId that = (ViewCountId) obj;

        if (this.novelId.equals(that.getNovelId())
                && this.episodeId.equals(that.episodeId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(novelId, episodeId);
    }
}
