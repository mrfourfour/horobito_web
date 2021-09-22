package myproject.demo.Novel.domain;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    @Embedded
    private AuthorId authorId;

    @Embedded
    private LastReadTime lastReadTime;

    private boolean deleted;

    private Novel(Title title, Description description, AuthorId authorId) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.deleted = false;
    }

    public static Novel create(Title title, Description description, AuthorId authorId){
        return new Novel(title, description, authorId);
    }


    public boolean checkDeleted(){
        return this.deleted;
    }

    public void delete(){
        this.deleted = true;
    }

    public void change(String title, String description){
        this.title = Title.create(title);
        this.description = Description.create(description);
    }

    public String getTitle(){
        return this.title.getTitle();
    }

    public String getDescription(){
        return this.description.getDescription();
    }

    public void renewalLastReadTime(){
        this.lastReadTime = LastReadTime.create(LocalDateTime.now());
    }

    public LocalDateTime getLastReadTime(){
        return this.lastReadTime.getLastReadTime();
    }

    public Long getAuthorId(){
        return this.authorId.getAuthorId();
    }
}
