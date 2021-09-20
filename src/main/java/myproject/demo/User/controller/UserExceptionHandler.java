package myproject.demo.User.controller;


import lombok.Value;
import myproject.demo.KeyCloak.service.DuplicateUserSignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"myproject.demo.User"})
public class UserExceptionHandler {

    @ExceptionHandler(value = DuplicateUserSignUpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse duplicateUserHandler(DuplicateUserSignUpException e){
        return new ErrorResponse("Duplicate User", "400");
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse NullPointerHandler(NullPointerException e){
        return new ErrorResponse("User doesn't exist", "400");
    }


}

@Value
class ErrorResponse{
    String errorMessage;
    String errorCode;
}
