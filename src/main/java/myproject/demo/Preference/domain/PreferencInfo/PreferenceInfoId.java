package myproject.demo.Preference.domain.PreferencInfo;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PreferenceInfoId implements Serializable {

    private Long novelId;

    private Long userId;

    private int episodeId;



    private PreferenceInfoId(Long novelId, Long userId, int episodeId) {
        this.novelId = novelId;
        this.userId = userId;
        this.episodeId = episodeId;

    }

    public static PreferenceInfoId create(Long novelId, Long userId, int episodeId){
        return new PreferenceInfoId(novelId, userId, episodeId);
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

        if (!(obj instanceof PreferenceInfoId)) {
            return false;
        }


        PreferenceInfoId that = (PreferenceInfoId) obj;

        if (this.novelId.equals(that.getNovelId())
                && this.userId.equals(that.userId)
                && this.episodeId==that.episodeId) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(novelId, userId, episodeId);
    }
}
