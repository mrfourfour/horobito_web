package myproject.demo.Preference.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PreferenceId implements Serializable {

    private Long novelId;

    private Long userId;


    private PreferenceId(Long novelId, Long userId) {
        this.novelId = novelId;
        this.userId = userId;
    }

    public static PreferenceId create(Long novelId, Long userId){
        return new PreferenceId(novelId, userId);
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

        if (obj instanceof CategoryNovelRelationId) {
            return false;
        }


        PreferenceId that = (PreferenceId) obj;

        if (this.novelId.equals(that.getNovelId()) && this.userId.equals(that.userId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(novelId, userId);
    }
}
