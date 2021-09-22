package myproject.demo.Novel.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    private String Title;

    private Title(String title) {
        Title = title;
    }

    public static Title create(String title){
        return new Title(title);
    }
}
