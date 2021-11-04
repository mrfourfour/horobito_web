package myproject.demo.updateTime.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.updateTime.domain.UpdateTime;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateTimeService {

    private final UpdateTimeRepository updateTimeRepository;

    @Transactional
    public UpdateTimeDto create(Long novelId){
        return getDto(updateTimeRepository.saveAndFlush(UpdateTime.create(novelId)));
    }

    private UpdateTimeDto getDto(UpdateTime updateTime) {
        return new UpdateTimeDto(
                updateTime.getId(),
                updateTime.getUpdateTime());
    }

    public UpdateTimeDto getUpdateTime(Long novelId){
        UpdateTime updateTime = updateTimeRepository.findById(novelId).get();
        updateTime.update();
        return getDto(updateTime);
    }

    private void update(Long novelId){
        updateTimeRepository.findById(novelId).get().update();
    }
}
