package myproject.demo.category_novel.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(CategoryNovelRelationId.class)
@NoArgsConstructor
public class CategoryNovelRelation {

    @Id
    private Long categoryId;

    @Id
    private Long novelId;

    private boolean deleted;

    private CategoryNovelRelation(Long categoryId, Long novelId) {
        this.categoryId = categoryId;
        this.novelId = novelId;
        this.deleted = false;
    }

    public static CategoryNovelRelation create(Long categoryId, Long novelId){
        return new CategoryNovelRelation(categoryId, novelId);
    }

    public CategoryNovelRelationId getId(){
        return CategoryNovelRelationId.create(this.categoryId, this.novelId);
    }

    public boolean checkDeleted(){
        return this.deleted;
    }

    public void delete(){
        this.deleted = true;
    }


}
