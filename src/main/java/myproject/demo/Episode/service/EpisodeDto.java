package myproject.demo.Episode.service;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EpisodeDto {

    Long novelId;
    int episodeId;
    String episodeTitle;
    String contentUrl;
    String authorComment;
    LocalDateTime registrationTime;
    int age;
    boolean deleted;
}
