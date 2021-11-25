package myproject.demo.Episode.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class EpisodeAge {

    int episodeAge;

    private EpisodeAge(int episodeAge) {
        this.episodeAge = episodeAge;
    }

    public static EpisodeAge create(int episodeAge){
        return new EpisodeAge(episodeAge);
    }
}
