package myproject.demo.User.domain.user;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password create(String password){
        return new Password(password);
    }
}
