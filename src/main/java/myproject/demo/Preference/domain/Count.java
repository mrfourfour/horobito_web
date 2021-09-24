package myproject.demo.Preference.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Count {

    private Long count;

    private Count(Long count) {
        this.count = count;
    }

    public static Count create(Long count){
        return new Count(count);
    }
}
