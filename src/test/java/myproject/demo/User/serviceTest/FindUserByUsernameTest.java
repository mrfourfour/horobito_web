package myproject.demo.User.serviceTest;

import myproject.demo.KeyCloak.service.DuplicateUserSignUpException;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.KeyCloak.service.TokenRequest;
import myproject.demo.User.controller.UserExceptionHandler;
import myproject.demo.User.domain.*;
import myproject.demo.User.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindUserByUsernameTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TokenProvider tokenProvider;

    @Mock
    UserExceptionHandler handler;

    @DisplayName(" Find By Username Test 1. normal condition ")
    @Test
    public void test1(){

        UserService sut = new UserService(userRepository, tokenProvider);

        Optional<User> user = Optional.of(User.create(
                Username.create("user"),
                Password.create("pwd"),
                Authority.create("auth")
        ));

        when(userRepository.findByUsername(any())).thenReturn(user);

        assertEquals("user",sut.findUserByUsername(any()).getUsername() );

    }

    @DisplayName(" Find By Username Test 2. abnormal condition - user doesn't exist ")
    @Test
    public void test2(){

        UserService sut = new UserService(userRepository, tokenProvider);



        assertThrows(NullPointerException.class, ()->sut.findUserByUserId(any()));


    }


}
