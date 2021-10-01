package myproject.demo.Episode.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class ContentURL {

    private String contentURL;

    private ContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public static ContentURL create(String contentURL){
        return new ContentURL(contentURL);
    }
}
