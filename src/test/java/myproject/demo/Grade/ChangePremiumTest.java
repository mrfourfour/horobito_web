package myproject.demo.Grade;

import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserDto;
import myproject.demo.User.service.UserService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.service.GradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ChangePremiumTest {

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    NovelService novelService;

    @Mock
    UserService userService;

    @Autowired
    NovelRepository novelRepository;

    @DisplayName("ChangePremium test 1. Normal Condition")
    @Test
    public void test1() {
        GradeService sut = new GradeService(
                gradeRepository, new NovelService(userService, novelRepository), userService);

        Long testGradeId = 1L; // author : 1L
        UserDto userDto = new UserDto(1L, "user1");

        //요청한 유저
        when(userService.findLoggedUser()).thenReturn(userDto);
        // 소설 작가
        when(userService.findUserByUserId(any())).thenReturn(userDto);

        Optional<Grade> sutObj = gradeRepository.findById(testGradeId);

        Assertions.assertTrue(sutObj.isPresent());
        Assertions.assertFalse(sutObj.get().getDeleted());
        boolean priorPremium = sutObj.get().getPremium();

        sut.changePremium(testGradeId, !priorPremium);


        Assertions.assertEquals(priorPremium, sutObj.get().getPremium());



    }


}
