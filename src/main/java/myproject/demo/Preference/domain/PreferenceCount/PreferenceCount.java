package myproject.demo.Preference.domain.PreferenceCount;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class PreferenceCount {

    @Id
    private Long id;

    @Embedded
    private Count count;

    private PreferenceCount(Long novelId ) {
        this.id = novelId;
        this.count = Count.create(0L);
    }



}
