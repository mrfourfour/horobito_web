package myproject.demo.updateTime.service;

import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.updateTime.domain.UpdateTime;
import myproject.demo.updateTime.domain.UpdateTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateTimeService {

    private final UpdateTimeRepository updateTimeRepository;

    private final NovelService novelService;

    private final UserService userService;

    @Transactional
    public UpdateTimeDto create(Long novelId){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        return getDto(updateTimeRepository.saveAndFlush(UpdateTime.create(novelId)));
    }

    @Transactional
    public void delete(Long novelId) {
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        updateTimeRepository.findById(novelId).ifPresent(
                selectedUpdate->{
                    checkAlreadyDeleted(selectedUpdate);
                    selectedUpdate.delete();
                });

    }

    @Transactional
    public UpdateTimeDto update(Long novelId){
        novelService.checkExistenceById(novelId);
        novelService.checkRequesterIdentity(
                userService.findLoggedUser().getUserId(),
                novelService.getNovelDto(novelId).getAuthorId());

        Optional<UpdateTime> selected = updateTimeRepository.findById(novelId);
        selected.ifPresent(selectedUpdateTime->{
            checkAlreadyDeleted(selectedUpdateTime);
            selectedUpdateTime.update(); });
        return getDto(selected.get());
    }

    private UpdateTimeDto getDto(UpdateTime updateTime) {
        return new UpdateTimeDto(
                updateTime.getId(),
                updateTime.getUpdateTime());
    }

    public UpdateTimeDto getUpdateTime(Long novelId){
        UpdateTime updateTime = updateTimeRepository.findById(novelId).get();
        return getDto(updateTime);
    }

    private void checkAlreadyDeleted(UpdateTime selectedUpdateTime) {
        if (selectedUpdateTime.isDeleted()){
            throw new IllegalArgumentException();
        }
    }
}