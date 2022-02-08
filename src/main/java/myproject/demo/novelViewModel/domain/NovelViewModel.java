package myproject.demo.novelViewModel.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class NovelViewModel {

    @Id
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


    private NovelViewModel(Long novelId, String title, String authorName, String coverImageUrl, String description, boolean deleted, int age, boolean premium, LocalDateTime updateTime, Long preferenceCount, int bookMarkCount, Long view, int episodeNumber) {
        this.novelId = novelId;
        this.title = title;
        this.authorName = authorName;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.deleted = deleted;
        this.age = age;
        this.premium = premium;
        this.updateTime = updateTime;
        this.preferenceCount = preferenceCount;
        this.bookMarkCount = bookMarkCount;
        this.view = view;
        this.episodeNumber = episodeNumber;
    }

    public static NovelViewModel create(Long novelId, String title, String authorName, String coverImageUrl, String description, boolean deleted, int age, boolean premium, LocalDateTime updateTime, Long preferenceCount, int bookMarkCount, Long view, int episodeNumber) {
        return new NovelViewModel(novelId, title, authorName, coverImageUrl, description, deleted, age, premium, updateTime, preferenceCount, bookMarkCount, view, episodeNumber);
    }

    public void updateNovelDomain(String title, String authorName, String coverImageUrl, String description, boolean deleted, int age) {
        this.title = title;
        this.authorName = authorName;
        this.coverImageUrl = coverImageUrl;
        this.description = description;
        this.deleted = deleted;
        this.age = age;
    }

    public void increasePrefer(){
        this.preferenceCount++;
    }

    public void decreasePrefer(){
        this.preferenceCount++;
    }

    public void updatePremium(boolean premium) {
        this.premium = premium;
    }

    public void updateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void updatePreferenceCount(Long preferenceCount) {
        this.preferenceCount = preferenceCount;
    }

    public void updateBookMarkCount(int bookMarkCount) {
        this.bookMarkCount = bookMarkCount;
    }

    public void updateViewCount(Long view) {
        this.view = view;

    }

    public void updateEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void delete() {
        if (this.deleted) {
            throw new IllegalArgumentException();
        }
        this.deleted = true;
    }

    public void resurrect (){
        if (!this.deleted){
            throw  new IllegalArgumentException();
        }
        this.deleted = false;
    }

    public void increaseBookmarkCount() {
        this.bookMarkCount++;
    }
    public void decreaseBookmarkCount() {
        this.bookMarkCount--;
    }

    public void increaseEpisodeCount() {
        this.episodeNumber ++;
    }

    public void decreaseEpisodeCount() {
        this.episodeNumber --;
    }
}
