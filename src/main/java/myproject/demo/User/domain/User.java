package myproject.demo.User.domain;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Username username;

    private Password password;

    private boolean deleted;


    private User(Username username, Password password) {
        this.username = username;
        this.password = password;
        this.deleted = false;
    }

    public static User create(Username username, Password password){
        return new User(username, password);
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
