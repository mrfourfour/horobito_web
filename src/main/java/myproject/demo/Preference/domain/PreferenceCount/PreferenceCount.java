package myproject.demo.Preference.domain.PreferenceCount;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(PreferenceCountId.class)
@Getter
@NoArgsConstructor
public class PreferenceCount {

    @Id
    private Long novelId;

    @Id
    private int episodeId;

    @Embedded
    private Count count;

    private boolean deleted;

    private PreferenceCount(Long novelId, int episodeId, Long count ) {
        this.novelId = novelId;
        this.episodeId = episodeId;
        this.count = Count.create(count);
        this.deleted= false;
    }

    public static PreferenceCount create(Long novelId, int episodeId, Long count ){
        return new PreferenceCount(novelId, episodeId, count);
    }

    public void increase(){
        this.count = Count.create(getCount()+1L);
    }

    public void decrease(){
        this.count = Count.create(getCount()-1L);
    }

    public int getEpisodeId() {
        return this.episodeId;
    }

    public void delete(){
        if (this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted = true;
    }
    public void resurrect(){
        if (!this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted = false;
    }

    public Long getNovelId() {
        return this.novelId;
    }

    public Long getCount(){
        return this.count.getCount();
    }



}
