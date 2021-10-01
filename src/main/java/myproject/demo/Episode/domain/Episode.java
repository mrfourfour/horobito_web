package myproject.demo.Episode.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@IdClass(EpisodeId.class)
@NoArgsConstructor
public class Episode {

    @Id
    private Long novelId;

    @Id
    private Long episodeNum;

    @Embedded
    private ContentURL contentURL;

    @Embedded
    private AuthorComment authorComment;

    private LocalDateTime registrationTime;

    private boolean deleted;

    private Episode(Long novelId, Long episodeNum, AuthorComment authorComment, ContentURL contentURL) {
        this.novelId = novelId;
        this.episodeNum = episodeNum;
        this.authorComment = authorComment;
        this.contentURL = contentURL;
        this.registrationTime = LocalDateTime.now();
    }

    public static Episode create(Long novelId, Long episodeNum,AuthorComment authorComment, ContentURL contentURL){
        return new Episode(novelId, episodeNum, authorComment, contentURL);
    }

    public boolean checkDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public void resurrect(){
        this.deleted = false;
    }

    public String getURL(){
        return this.contentURL.getContentURL();
    }

    public void changeContent(String contentURL){
        this.contentURL = ContentURL.create(contentURL);
    }

    public String getAuthorComment(){
        return this.authorComment.getAuthorComment();
    }

    public void changeAuthorComment(String newComment){
        this.authorComment = AuthorComment.create(newComment);
    }


    private LocalDateTime getRegistTime(){
        return this.registrationTime;
    }


}
