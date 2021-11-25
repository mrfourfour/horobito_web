package myproject.demo.Episode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class EpisodeTitle {

    String episodeTitle;

    private EpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }


    public static EpisodeTitle create(String episodeTitle){
        return new EpisodeTitle(episodeTitle);
    }
}
