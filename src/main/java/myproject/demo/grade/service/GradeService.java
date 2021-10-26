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
    public void create(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        gradeRepository.save(Grade.create(novelId, Premium.create(premium)));
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



}
