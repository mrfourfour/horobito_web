package myproject.demo.grade.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.domain.Premium;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final NovelService novelService;
    private final UserService userService;

    @Transactional
    public GradeDto create(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        return getGradeDto(gradeRepository.saveAndFlush(Grade.create(novelId, Premium.create(premium))));
    }

    @Transactional
    public void delete(Long novelId){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        checkExistence(novelId);
        gradeRepository.findById(novelId).get().delete();
    }

    @Transactional
    public void resurrect(Long id){
        novelService.checkExistenceById(id);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(id).getAuthorId());

        checkJustExistence(id);
        gradeRepository.findById(id).ifPresent(selectedGrade->{
                    checkNonAlive(selectedGrade);
                    selectedGrade.resurrect(); });
    }

    private void checkNonAlive(Grade selectedGrade) {
        if (!selectedGrade.getDeleted()){
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public GradeDto changePremium(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        checkExistence(novelId);
//        checkPremium(novelId, premium);
        gradeRepository.findById(novelId).get().changePremium(premium);
        return getGradeDto(novelId, premium);
    }

    private GradeDto getGradeDto(Grade grade) {
        return new GradeDto(grade.getId(), grade.getPremium());
    }

    private GradeDto getGradeDto(Long novelId, boolean premium) {
        return new GradeDto(novelId, premium);
    }

    private void checkPremium(Long novelId, boolean premium) {
        if (gradeRepository.findById(novelId).get().getPremium()==premium){
            throw new IllegalArgumentException();
        }
    }

    private void checkJustExistence(Long novelId) {
        if (gradeRepository.findById(novelId).isEmpty()){
            throw new IllegalArgumentException();
        }
    }

    private void checkExistence(Long novelId) {
        if (gradeRepository.findById(novelId).isEmpty()
                || gradeRepository.findById(novelId).get().getDeleted()){
            throw new IllegalArgumentException();
        }
    }

    public List<Long> getNovelIdsByGrade(boolean premium){
        return gradeRepository
                .findAllByPremium_PremiumAndDeleted(premium, false)
                .stream().map(Grade::getId).collect(Collectors.toList());

    }

    public List<Long> getAllNovelIds(){
        return gradeRepository
                .findAllByDeleted(false)
                .stream().map(Grade::getId).collect(Collectors.toList());

    }


    public GradeDto getGrade(Long novelId) {
        return getGradeDto(gradeRepository.findById(novelId).get());

    }
}
