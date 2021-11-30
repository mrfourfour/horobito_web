package myproject.demo.Preference.domain.TotalPreferenceCount;


import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class TotalPreferenceCount {

    @Id
    Long novelId;

    @Embedded
    private TotalCount totalCount;

    boolean deleted;

    private TotalPreferenceCount(Long novelId, TotalCount totalCount) {
        this.novelId = novelId;
        this.totalCount = totalCount;
        this.deleted = false;
    }

    public static TotalPreferenceCount create(Long novelId, Long totalCount){
        return new TotalPreferenceCount(novelId, TotalCount.create(totalCount));
    }

    public void increase(){
        this.totalCount = TotalCount.create(this.totalCount.totalCount+1);
    }

    public void increase(Long increasedPreference){
        this.totalCount = TotalCount.create(this.totalCount.totalCount+increasedPreference);
    }

    public void decrease(){
        this.totalCount = TotalCount.create(this.totalCount.totalCount-1);
    }

    public void decrease(Long deletedPreference){
        this.totalCount = TotalCount.create(this.totalCount.totalCount-deletedPreference);
    }

    public Long getNovelId(){
        return this.novelId;
    }

    public Long getTotalCount(){
        return this.totalCount.totalCount;
    }

    public void delete(){
        if (this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted =true;
    }

    public void resurrect(){
        if (!this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted =false;
    }




}
