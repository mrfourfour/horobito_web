package myproject.demo.User.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.User.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Token token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Token> refresh (@RequestBody RefreshTokenPayload refreshTokenPayLoad){
        return ResponseEntity.ok(refresh(refreshTokenPayLoad.refreshToken));
    }

    private Token refresh(String refreshToken) {
        return tokenProvider.refresh(refreshToken);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest.username,
                signUpRequest.password,
                "ROLE_USER",
                LocalDate.parse(signUpRequest.birthDay),
                signUpRequest.gender);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up/admin")
    public ResponseEntity<Void> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest.username,
                signUpRequest.password,
                "ROLE_ADMIN",
                LocalDate.parse(signUpRequest.birthDay),
                signUpRequest.gender);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email-duplicate-check/{username}")
    public ResponseEntity<Void> checkDuplicateUser(@PathVariable String username){
        userService.checkDuplicateUser(username);
        return ResponseEntity.ok().build();
    }



}

@Value
class LoginRequest{
    String username;
    String password;
}
@Value
class LoginResponse{
    Token token;
    public LoginResponse(Token token) {
        this.token = token;
    }
}
@Value
class RefreshTokenPayload {
    public String refreshToken;
}
@Value
class SignUpRequest{
    public String username;
    public String password;
    public String birthDay;
    public String gender;
}

@Value
class CheckedUserName{
    public String username;
}

