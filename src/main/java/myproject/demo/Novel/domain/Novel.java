package myproject.demo.Novel.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


@Entity
@Getter
@Setter
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
    private Age age;

    @Embedded
    CoverImageUrl coverImageUrl;




    private boolean deleted;

    private Novel(Title title, Description description, AuthorId authorId, Age age, CoverImageUrl coverImageUrl) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.deleted = false;
        this.age = age;
        this.coverImageUrl = coverImageUrl;

    }

    public static Novel create( Title title, Description description, AuthorId authorId, Age age, CoverImageUrl coverImageUrl) {
        return new Novel(title, description, authorId, age, coverImageUrl);
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

    public void resurrect() {
        if (!this.deleted){
            throw new IllegalArgumentException();
        }
        this.deleted = false;
    }

    public void change(String title, String description, int age, String coverImageUrl) {
        this.title = Title.create(title);
        this.description = Description.create(description);
        this.age = Age.create(age);
        this.coverImageUrl = CoverImageUrl.create(coverImageUrl);
    }

    public String getTitle() {
        return this.title.getTitle();
    }

    public String getDescription() {
        return this.description.getDescription();
    }

    public Long getAuthorId() {
        return this.authorId.getAuthorId();
    }

    public int getAge(){
        return this.age.getAge();
    }

    public String getCoverImageUrl(){
        return this.coverImageUrl.getUrl();
    }



}
