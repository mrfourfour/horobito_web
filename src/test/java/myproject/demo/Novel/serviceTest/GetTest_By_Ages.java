package myproject.demo.Novel.serviceTest;


import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelDto;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.domain.user.UserRepository;
import myproject.demo.User.service.UserService;
import myproject.demo.User.service.UsernameDuplicateChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GetTest_By_Ages {

    @Autowired
    NovelRepository novelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UsernameDuplicateChecker checker;


    @Autowired
    UserService userService;


    @DisplayName("GetByAge Test 1. Normal Condition")
    @Test
    public void test1() {

        NovelService sut = new NovelService(userService, novelRepository);

        List<Long> novelIds = Arrays.asList(1L,2L,3L,4L,5L,6L, 7L, 8L, 9L, 10L);

        int age = 12;

        List<NovelDto> novelDtos = sut.getNovelsByAge(novelIds, 13);


        for (NovelDto novelDto : novelDtos){
            System.out.println(novelDto.toString());
        }




    }

}
