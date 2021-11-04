package myproject.demo.Novel.serviceTest;


import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTest {

    @Mock
    UserService userService;

    @Mock
    NovelRepository novelRepository;

    @DisplayName("Create Test 1. Normal Condition")
    @Test
    public void test1(){
        NovelService sut = new NovelService(userService, novelRepository);


    }
}
