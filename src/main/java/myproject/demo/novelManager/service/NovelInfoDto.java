package myproject.demo.novelManager.service;

import lombok.Value;
import myproject.demo.category.service.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class NovelInfoDto{
    Long novelId;
    String title;
    String authorName;
    String coverImageUrl;
    String description;
    LocalDateTime updateTime;
    boolean premium;
    Long preferenceCount;
    int bookMarkCount;
    Long view;
    int episodeNumber;
    boolean deleted;
    List<CategoryDto> categoryDtos;

}
