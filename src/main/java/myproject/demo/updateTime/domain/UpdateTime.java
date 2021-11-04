package myproject.demo.updateTime.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Getter
@NoArgsConstructor
public class UpdateTime {

    @Id
    private Long id;

    private LocalDateTime updateTime;

    private boolean deleted;

    private UpdateTime(Long id) {
        this.id = id;
        this.updateTime = LocalDateTime.now();
        this.deleted = false;
    }

    public static UpdateTime create(Long id){
        return new UpdateTime(id);
    }


    public void update(){
        this.updateTime = LocalDateTime.now();
    }
}
