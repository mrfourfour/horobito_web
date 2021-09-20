package myproject.demo.User.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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

    private boolean deleted;

    @Embedded
    private Authority auth;


    private User(Username username, Password password, Authority auth) {
        this.username = username;
        this.password = password;
        this.deleted = false;
        this.auth = auth;
    }

    public static User create(Username username, Password password, Authority auth){
        return new User(username, password, auth);
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

}
