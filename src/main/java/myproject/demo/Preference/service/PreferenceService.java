package myproject.demo.Preference.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferenceRepository;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final NovelService novelService;

    private final UserService userService;

    private final PreferenceRepository preferenceRepository;
}
