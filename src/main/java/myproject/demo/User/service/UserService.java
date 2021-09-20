package myproject.demo.User.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.KeyCloak.service.DuplicateUserSignUpException;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.KeyCloak.service.TokenRequest;
import myproject.demo.User.domain.Password;
import myproject.demo.User.domain.User;
import myproject.demo.User.domain.UserRepository;
import myproject.demo.User.domain.Username;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    public Token login(String username, String password){
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        return tokenProvider.issue(tokenRequest);
    }

    public void signUp(String username, String password) {
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        tokenProvider.signUp(tokenRequest);
        save(username, password);
    }

    public void save(String username, String password){
        User user = User.create(Username.create(username), Password.create(password));
        userRepository.save(user);
    }


    public void checkDuplicateUser(String usernname){
        if (userRepository.existsByUsername(Username.create(usernname))){
            throw new DuplicateUserSignUpException();
        }
    }
}
