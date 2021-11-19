package myproject.demo.bookmark.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(BookMarkId.class)
@Getter
@NoArgsConstructor
public class BookMark {

    @Id
    private Long userId;

    @Id
    private Long novelId;

    private boolean deleted;

    private BookMark(Long userId, Long novelId) {
        this.userId = userId;
        this.novelId = novelId;
        this.deleted = false;
    }

    public static BookMark create(Long userId, Long novelId){
        return new BookMark(userId, novelId);
    }

    public void delete(){
        this.deleted = true;
    }

    public void resurrect(){
        this.deleted = false;
    }
}
