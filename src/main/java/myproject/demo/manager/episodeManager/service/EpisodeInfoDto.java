package myproject.demo.manager.episodeManager.service;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EpisodeInfoDto {
    Long novelId;
    int episodeNum;
    String episodeTitle;
    String contentUrl;
    String authorComment;
    LocalDateTime wrtTime;
    int episodeAge;
    boolean deleted;

}
