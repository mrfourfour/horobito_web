package myproject.demo.Novel.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {

    private int age;

    private Age(int age) {
        this.age = age;
    }

    public static Age create(int age){
        return new Age(age);
    }
}
