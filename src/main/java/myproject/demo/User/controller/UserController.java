package myproject.demo.User.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.User.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
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
                signUpRequest.birthDay,
                signUpRequest.gender);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up/admin")
    public ResponseEntity<Void> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        userService.signUp(signUpRequest.username,
                signUpRequest.password,
                "ROLE_ADMIN",
                signUpRequest.birthDay,
                signUpRequest.gender);
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    public LocalDateTime birthDay;
    public String gender;
}


