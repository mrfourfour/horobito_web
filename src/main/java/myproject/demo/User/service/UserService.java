package myproject.demo.User.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.KeyCloak.service.TokenRequest;
import myproject.demo.User.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    private final UsernameDuplicateChecker usernameDuplicateChecker;

    public Token login(String username, String password){
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        return tokenProvider.issue(tokenRequest);
    }

    public void signUp(String username, String password, String auth, LocalDateTime birthDay, String gender) {
        checkDuplicateUser(username);
        TokenRequest tokenRequest
                = TokenRequest.create(username, password);
        tokenProvider.signUp(tokenRequest);
        save(username, password, auth, birthDay, gender);
    }

    public void save(String username, String password, String auth, LocalDateTime birthDay, String gender){
        User user = User.create(
                Username.create(username),
                Password.create(password),
                Authority.create(auth),
                birthDay,
                Gender.valueOf(gender)
                );
        userRepository.save(user);
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
        return getUserDto(searchedUser);

    }

    public UserDto findLoggedUser(){
        return findUserByUsername(getLoggedUsername());
    }

    private String getLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public UserDto findUserByUserId(Long userId){
        Optional<User> searchedUser = userRepository.findById(userId);
        checkEmpty(searchedUser);
        return getUserDto(searchedUser);
    }

    private void checkEmpty(Optional<User> searchedUser) {
        if (searchedUser.isEmpty()){
            throw new NullPointerException();
        }
    }

    public UserDto getUserDto(Optional<User> user){
        return new UserDto(user.get().getUserId(), user.get().getUsername());
    }
}
