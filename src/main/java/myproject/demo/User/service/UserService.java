package myproject.demo.User.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.KeyCloak.service.TokenRequest;
import myproject.demo.User.domain.user.*;
import org.bouncycastle.util.Strings;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    private final UsernameDuplicateChecker usernameDuplicateChecker;

    private final LoggedUserInfo loggedUserInfo;
    public Token login(String username, String password){
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        return tokenProvider.issue(tokenRequest);

    }

    @Transactional
    public void signUp(String username, String password, String auth, LocalDate birthDay, String gender) {
        checkDuplicateUser(username);
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        tokenProvider.signUp(tokenRequest);
         saveUser(username, password, auth, birthDay, gender);

    }

    public User saveUser(String username, String password, String auth, LocalDate birthDay, String gender){
        User user = User.create(
                Username.create(username),
                Password.create(password),
                Authority.create(auth),
                birthDay,
                Gender.create(Strings.toUpperCase(gender))
                );
        return userRepository.saveAndFlush(user);
    }


    public void checkDuplicateUser(String username){
        usernameDuplicateChecker.checkDuplicate(username);
    }

    public boolean checkExistenceByUsername(String username){
        return userRepository.existsByUsername(Username.create(username));
    }

    public boolean checkExistenceByUserId(Long id){
        return userRepository.existsById(id);
    }

    public UserDto findUserByUsername(String username){
        Optional<User> searchedUser = userRepository.findByUsername(Username.create(username));
        return getUserDto(searchedUser.get());

    }

    public UserDto findLoggedUser(){
        return findUserByUsername(getLoggedUsername());
    }

    public String getLoggedUsername() {
        return loggedUserInfo.getLoggedUsername();
    }

    public UserDto findUserByUserId(Long userId){
        Optional<User> searchedUser = userRepository.findById(userId);
        checkEmpty(searchedUser);
        return getUserDto(searchedUser.get());
    }

    private void checkEmpty(Optional<User> searchedUser) {
        if (searchedUser.isEmpty()){
            throw new NullPointerException();
        }
    }

    public UserDto getUserDto(User user){
        return new UserDto(user.getUserId(), user.getUsername());
    }
}
