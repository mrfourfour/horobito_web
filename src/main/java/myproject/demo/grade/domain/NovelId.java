package myproject.demo.grade.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelId {

    private Long novelId;

    private NovelId(Long novelId) {
        this.novelId = novelId;
    }

    public static NovelId create(Long novelId){
        return new NovelId();
    }

}
