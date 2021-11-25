package myproject.demo.User.domain.user;


import lombok.NoArgsConstructor;
import org.bouncycastle.util.Strings;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Gender gender;

    private boolean deleted;

    private LocalDate birthDay;

    @Embedded
    private Authority auth;




    private User(Username username, Password password, Authority auth, LocalDate birthDay, Gender gender) {
        this.username = username;
        this.password = password;
        this.deleted = false;
        this.birthDay = birthDay;
        this.auth = auth;
        this.gender = gender;
    }

    public static User create(Username username, Password password, Authority auth, LocalDate birthDay, Gender gender){
        return new User(username, password, auth, birthDay, gender);
    }

    public Long getUserId(){
        return this.id;
    }

    public String getUsername(){
        return this.username.getUsername();
    }

    public String getPassword(){
        return this.password.getPassword();
    }



    public void delete(){
        this.deleted = true;
    }

    public boolean checkDeleted(){
        return this.deleted;
    }

    public void changePassword(String changePassword){
        this.password = Password.create(changePassword);
    }

    public void changeUsername(String username){
        this.username = Username.create(username);
    }

    public void changeBirthday(LocalDate newBirthDay){
        this.birthDay = newBirthDay;
    }

    public LocalDate getBirthDay(){
        return this.birthDay;
    }

    public Gender getGender(){
        return this.gender;
    }

    public void changeGender(String gender){
        this.gender = Gender.create(Strings.toUpperCase(gender));
    }

}
