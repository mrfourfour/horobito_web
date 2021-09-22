package myproject.demo.category.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CategoryName categoryName;

    private Category(CategoryName categoryName) {
        this.categoryName = categoryName;
        this.deleted = false;
    }

    public static Category create(CategoryName categoryName){
        return new Category(categoryName);
    }

    public void change(String newName){
        this.categoryName = CategoryName.create(newName);
    }

    private boolean deleted;

    public boolean checkDeleted(){
        return this.deleted;
    }

    public void delete(){
        this.deleted = true;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.categoryName.getCategoryName();
    }

}
