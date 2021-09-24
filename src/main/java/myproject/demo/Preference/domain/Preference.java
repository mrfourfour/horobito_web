package myproject.demo.Preference.domain;

import lombok.NoArgsConstructor;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;

import javax.persistence.*;

@Entity
@IdClass(PreferenceId.class)
@NoArgsConstructor
public class Preference {

    @Id
    private Long novelId;

    @Id
    private Long userId;

    @Embedded
    private Count count;

    private boolean deleted;

    private Preference(Long novelId, Long userId, Count count) {
        this.novelId = novelId;
        this.userId = userId;
        this.count = count;
        this.deleted = false;
    }

    public static Preference create(Long novelId, Long userId, Count count){
        return new Preference(novelId, userId, count);
    }

    public PreferenceId getId(){
        return PreferenceId.create(this.novelId, this.userId);
    }

    public boolean checkDeleted(){
        return this.deleted;
    }

    public void  delete(){
        this.deleted = true;
    }

    public Long getNovelId(){
        return this.novelId;
    }

    public Long getUserId(){
        return this.userId;
    }

    public Long getCount(){
        return this.count.getCount();
    }

    public void increaseCount(){
        this.count = Count.create(this.count.getCount()+1);
    }

    public void decreaseCount(){
        this.count = Count.create(this.count.getCount()-1);
    }
}
