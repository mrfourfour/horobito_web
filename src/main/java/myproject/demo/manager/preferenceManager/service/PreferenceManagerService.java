package myproject.demo.manager.preferenceManager.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Preference.service.PreferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceManagerService {


    private final PreferenceService preferenceService;

    @Transactional
    public void preferEpisode(Long novelId, int episodeId) {
        preferenceService.prefer(novelId, episodeId);
    }

    @Transactional
    public void cancelPreference(Long novelId, int episodeId) {
        preferenceService.cancel(novelId, episodeId);
    }
}
