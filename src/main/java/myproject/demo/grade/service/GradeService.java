package myproject.demo.grade.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.domain.NovelId;
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

    @Transactional
    public GradeDto create(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        return getGradeDto(gradeRepository.saveAndFlush(Grade.create(novelId, Premium.create(premium))));
    }

    private GradeDto getGradeDto(Grade grade) {
        return new GradeDto(grade.getNovelId(), grade.getPremium());
    }

    private GradeDto getGradeDto(Long novelId, boolean premium) {
        return new GradeDto(novelId, premium);
    }

    @Transactional
    public void delete(Long novelId){
        novelService.checkExistenceById(novelId);
        checkExistence(novelId);
        gradeRepository.findById(novelId).get().delete();
    }

    @Transactional
    public void resurrect(Long novelId){
        novelService.checkExistenceById(novelId);
        checkExistence(novelId);
    }

    @Transactional
    public GradeDto changePremium(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        checkExistence(novelId);
        checkPremium(novelId, premium);
        gradeRepository.findById(novelId).get().changePremium(premium);
        return getGradeDto(novelId, premium);
    }

    private void checkPremium(Long novelId, boolean premium) {
        if (gradeRepository.findById(novelId).get().getPremium()==premium){
            throw new IllegalArgumentException();
        }
    }

    private void checkExistence(Long novelId) {
        if (!gradeRepository.findById(novelId).isPresent()
                || gradeRepository.findById(novelId).get().getDeleted()){
            throw new IllegalArgumentException();
        }
    }

    public List<Long> getNovelIdsByGrade(boolean premium){
        return gradeRepository
                .findAllByPremium_PremiumAndDeleted(premium, false)
                .stream().map(Grade::getNovelId).collect(Collectors.toList());

    }

    public List<Long> getAllNovelIds(){
        return gradeRepository
                .findAllByDeleted(false)
                .stream().map(Grade::getNovelId).collect(Collectors.toList());

    }


    public GradeDto getGrade(Long novelId) {
        return getGradeDto(gradeRepository.findById(novelId).get());

    }
}
