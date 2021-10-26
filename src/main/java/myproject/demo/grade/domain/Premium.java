package myproject.demo.grade.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Premium {

    private boolean premium;

    private Premium(boolean premium) {
        this.premium = premium;
    }

    public static Premium create(boolean premium){
        return new Premium(premium);
    }
}
