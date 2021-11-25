package myproject.demo.Episode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class AuthorComment {

    private String authorComment;

    private AuthorComment(String authorComment) {
        this.authorComment = authorComment;
    }

    public static AuthorComment create(String authorComment){
        if (authorComment==null || authorComment.length()==0){
            authorComment = "";
        }
        return new AuthorComment(authorComment);
    }
}
