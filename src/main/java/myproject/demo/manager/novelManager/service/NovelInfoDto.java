package myproject.demo.manager.novelManager.service;

import lombok.Value;
import myproject.demo.category.service.CategoryDto;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class NovelInfoDto {
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
    List<CategoryDto> categoryDtos;

}
