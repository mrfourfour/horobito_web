package myproject.demo.grade.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.grade.domain.Grade;
import myproject.demo.grade.domain.GradeRepository;
import myproject.demo.grade.domain.NovelId;
import myproject.demo.grade.domain.Premium;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final NovelService novelService;

    public void create(Long novelId, boolean premium){
        novelService.checkExistenceById(novelId);
        gradeRepository.save(Grade.create(NovelId.create(novelId), Premium.create(premium)));
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
