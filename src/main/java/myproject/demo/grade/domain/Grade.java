package myproject.demo.grade.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NovelId novelId;

    @Embedded
    private Premium premium;

    private boolean deleted;


    private Grade(NovelId novelId, Premium premium) {
        this.novelId = novelId;
        this.premium = premium;
        this.deleted = false;
    }

    public static Grade create(NovelId novelId, Premium premium){
        return new Grade(novelId, premium);
    }

    public Long getNovelId(){
        return this.novelId.getNovelId();
    }

    public boolean getPremium(){
        return this.premium.isPremium();
    }

    public void changePremium(boolean premium){
        this.premium = Premium.create(premium);
    }

    public void delete(){
        this.deleted = true;
    }

    public void resurrect(){
        this.deleted = false;

    }
}
