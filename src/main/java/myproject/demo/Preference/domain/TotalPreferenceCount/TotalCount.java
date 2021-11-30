package myproject.demo.Preference.domain.TotalPreferenceCount;


import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class TotalCount {

    Long totalCount;

    private TotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public static TotalCount create(Long totalCount){
        return new TotalCount(totalCount);
    }
}
