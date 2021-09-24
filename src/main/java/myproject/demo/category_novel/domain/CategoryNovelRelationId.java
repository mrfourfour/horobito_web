package myproject.demo.category_novel.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class CategoryNovelRelationId implements Serializable {

    @NotNull
    private Long categoryId;
    @NotNull
    private Long novelId;


    private CategoryNovelRelationId(Long categoryId, Long novelId) {
        this.categoryId = categoryId;
        this.novelId = novelId;
    }

    public static CategoryNovelRelationId create(Long categoryId, Long novelId){
        return new CategoryNovelRelationId(categoryId, novelId);
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


        CategoryNovelRelationId that = (CategoryNovelRelationId) obj;

        if (this.categoryId.equals(that.categoryId) && this.novelId.equals(that.novelId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(categoryId, novelId);
    }

}
