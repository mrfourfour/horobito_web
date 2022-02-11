package myproject.demo.manager.viewManager.novelDetails.service;

import lombok.Value;
import myproject.demo.Episode.service.EpisodeDto;
import myproject.demo.category.service.CategoryDto;

import java.util.List;

@Value
public class NovelDetailsDto {

    String title;
    Long novelId;
    boolean premium;
    List<CategoryDto> categoryDtos;
    int episodeCount;
    Long preferenceCount;
    int bookmarkCount;
    boolean deleted;
    List<EpisodeDto> episodeDtoList;
    boolean alreadyBookmark;
}
