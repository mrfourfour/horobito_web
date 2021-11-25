package myproject.demo.Preference.domain.PreferenceCount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoId;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PreferenceCountId implements Serializable {

    private Long novelId;

    private int episodeId;


    private PreferenceCountId(Long novelId, int episodeId) {
        this.novelId = novelId;
        this.episodeId = episodeId;
    }

    public static PreferenceCountId create(Long novelId, int episodeId){
        return new PreferenceCountId(novelId, episodeId);
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

        if (!(obj instanceof PreferenceCountId)) {
            return false;
        }


        PreferenceCountId that = (PreferenceCountId) obj;

        if (this.novelId.equals(that.getNovelId())
                && this.episodeId== that.episodeId) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(novelId, episodeId);
    }
}
