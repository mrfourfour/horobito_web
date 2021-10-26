package myproject.demo.grade.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Grade {

    @Id
    private Long novelId;

    @Embedded
    private Premium premium;

    private boolean deleted;


    private Grade(Long novelId, Premium premium) {
        this.novelId = novelId;
        this.premium = premium;
        this.deleted = false;
    }

    public static Grade create(Long novelId, Premium premium){
        return new Grade(novelId, premium);
    }

    public Long getNovelId(){
        return this.novelId;
    }

    public boolean getDeleted(){
        return this.deleted;
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
