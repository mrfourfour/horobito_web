package myproject.demo.Episode.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Episode.domain.EpisodeRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final NovelService novelService;

    private final UserService userService;

    private final EpisodeRepository episodeRepository;
}
