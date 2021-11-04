package myproject.demo.bookmark.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class BookMarkId implements Serializable {

    @NotNull
    private Long userId;

    @NotNull
    private Long novelId;

    private BookMarkId(@NotNull Long userId, @NotNull Long novelId) {
        this.userId = userId;
        this.novelId = novelId;
    }

    public static BookMarkId create(@NotNull Long userId, @NotNull Long novelId){
        return new BookMarkId(userId, novelId);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }

        if (!(obj instanceof BookMarkId)) {
            return false;
        }


        BookMarkId that = (BookMarkId) obj;

        if (this.userId.equals(that.userId) && this.novelId.equals(that.novelId)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId, novelId);
    }
}
