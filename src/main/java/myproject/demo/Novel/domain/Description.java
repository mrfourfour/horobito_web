package myproject.demo.Novel.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Description {
    private String description;

    private Description(String description) {
        this.description = description;
    }

    public static Description create(String description){
        return new Description(description);
    }
}
