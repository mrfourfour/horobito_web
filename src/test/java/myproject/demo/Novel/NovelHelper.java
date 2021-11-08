package myproject.demo.Novel;

import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.*;
import myproject.demo.Novel.service.NovelDto;

public class NovelHelper {
    private NovelHelper(){

    }


    public static Novel create(
            Long novelId,
            Long authorId,
            String title,
            String description,
            int age,
            String coverImageUrl

    ){
        Novel novel = Novel.create(
                Title.create(title),
                Description.create(description),
                AuthorId.create(authorId),
                Age.create(age),
                CoverImageUrl.create(coverImageUrl));

        novel.setId(novelId);
        return novel;
    }
}
