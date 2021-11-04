package myproject.demo.Novel.service;

import lombok.Value;

@Value
public class NovelDto {
    Long novelId;
    Long authorId;
    String title;
    String description;
    String authorName;
    String coverImageUrl;
    boolean deleted;
}
