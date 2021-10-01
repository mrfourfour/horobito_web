package myproject.demo.Preference.domain.PreferenceCount;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class Count {

    Long count;

    private Count(Long count) {
        this.count = count;
    }

    public static Count create(Long count){
        return new Count(count);
    }
}
