package myproject.demo.User.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

    private String username;

    private Username(String username) {
        this.username = username;
    }

    public static Username create(String username){
        return new Username(username);
    }
}
