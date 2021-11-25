package myproject.demo.view.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@IdClass(ViewCountId.class)
@NoArgsConstructor
public class ViewCount {

    @Id
    private Long novelId;

    @Id
    private int episodeId;

    @Embedded
    private Count count;

    private boolean deleted;

    private ViewCount(Long novelId, int episodeId, Long count ) {
        this.novelId = novelId;
        this.episodeId = episodeId;
        this.count = Count.create(count);
    }

    public static ViewCount create(Long novelId, int episodeId, Long count ){
        return new ViewCount(novelId, episodeId, count);
    }

    public void increase(){
        this.count = Count.create(getCount()+1L);
    }

    public void decrease(){
        this.count = Count.create(getCount()-1L);
    }

    public void delete(){
        if (this.deleted){
            throw new IllegalArgumentException();
        }
        deleted =true;
    }
    public void resurrect(){
        if (!this.deleted){
            throw new IllegalArgumentException();
        }
        deleted =false;
    }

    public int getEpisodeId() {
        return this.episodeId;
    }

    public Long getNovelId() {
        return this.novelId;
    }

    public Long getCount(){
        return this.count.getCount();
    }



}
