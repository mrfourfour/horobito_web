package myproject.demo.Novel.domain;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.Strings;

import javax.persistence.*;

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
    private Age age;




    private boolean deleted;

    private Novel(Title title, Description description, AuthorId authorId, Age age) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.deleted = false;
        this.age = age;

    }

    public static Novel create( Title title, Description description, AuthorId authorId, Age age) {
        return new Novel(title, description, authorId, age);
    }

    public boolean checkDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public void change(String title, String description, int age) {
        this.title = Title.create(title);
        this.description = Description.create(description);
        this.age = Age.create(age);
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



}
