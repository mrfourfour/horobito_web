package myproject.demo.Novel.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverImageUrl {

    private String url;

    private CoverImageUrl(String url) {
        this.url = url;
    }

    public static CoverImageUrl create(String url){
        return new CoverImageUrl(url);
    }
}
