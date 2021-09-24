package myproject.demo.Novel.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorId {
    private Long authorId;

    private AuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public static AuthorId create(Long authorId){
        return new AuthorId();
    }
}
