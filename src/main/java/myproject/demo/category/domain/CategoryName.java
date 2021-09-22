package myproject.demo.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryName {

    private String categoryName;

    private CategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static CategoryName create(String categoryName){
        return new CategoryName(categoryName);
    }
}
