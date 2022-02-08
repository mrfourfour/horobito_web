package myproject.demo.novelViewModel.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class NovelViewModelDto {

    Long novelId;
    String title;
    String authorName;
    String coverImageUrl;
    String description;
    boolean deleted;
    int age;
    boolean premium;
    LocalDateTime updateTime;
    Long preferenceCount;
    int bookMarkCount;
    Long view;
    int episodeNumber;


}
