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
    private int episodeNum;

    @Embedded
    private EpisodeTitle episodeTitle;

    @Embedded
    private ContentURL contentURL;

    @Embedded
    private AuthorComment authorComment;

    private LocalDateTime registrationTime;

    private EpisodeAge episodeAge;

    private boolean deleted;

    private Episode(Long novelId, int episodeNum, String title, AuthorComment authorComment, ContentURL contentURL, int age) {
        this.novelId = novelId;
        this.episodeNum = episodeNum;
        this.episodeTitle = EpisodeTitle.create(title);
        this.authorComment = authorComment;
        this.contentURL = contentURL;
        this.registrationTime = LocalDateTime.now();
        this.episodeAge = EpisodeAge.create(age);
    }

    public static Episode create(Long novelId, int episodeNum, String title, AuthorComment authorComment, ContentURL contentURL, int age){
        return new Episode(novelId, episodeNum, title, authorComment, contentURL, age);
    }

    public void change(String title, String authorComment, String contentURL, int age){
        this.episodeTitle = EpisodeTitle.create(title);
        this.authorComment = AuthorComment.create(authorComment);
        this.contentURL = ContentURL.create(contentURL);
        this.episodeAge = EpisodeAge.create(age);

    }

    public boolean checkDeleted() {
        return this.deleted;
    }

    public void delete() {
        if (this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted = true;
    }

    public void resurrect(){
        if (!this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted = false;
    }

    public String getURL(){
        return this.contentURL.getContentUrl();
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

    public int getAge(){
        return this.episodeAge.episodeAge;
    }

    public String getTitle(){
        return this.episodeTitle.getEpisodeTitle();
    }

    public void changeTitle(String title){
        this.episodeTitle = EpisodeTitle.create(title);
    }

    public void changeAge(int age){
        this.episodeAge = EpisodeAge.create(age);
    }


    private LocalDateTime getRegistTime(){
        return this.registrationTime;
    }


}
