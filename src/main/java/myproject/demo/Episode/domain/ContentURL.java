package myproject.demo.Episode.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class ContentURL {

    private String contentUrl;

    private ContentURL(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public static ContentURL create(String contentUrl){
        return new ContentURL(contentUrl);
    }
}
