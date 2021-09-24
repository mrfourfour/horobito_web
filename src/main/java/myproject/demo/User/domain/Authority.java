package myproject.demo.User.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Authority {


    String authority;


    private Authority(String authority) {
        this.authority = authority;
    }

    public static Authority create(String authority){
        return new Authority(authority);
    }
}
