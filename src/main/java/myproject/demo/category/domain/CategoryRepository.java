package myproject.demo.category.domain;

import myproject.demo.category.service.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(CategoryName categoryName);

    List<Category> findAllByDeleted(boolean deleted);

    Optional<Category> findByCategoryNameAndDeleted(CategoryName categoryName, boolean deleted);

    Optional<Category> findByIdAndDeleted(Long id, boolean deleted);

    Optional<Category> findByCategoryName(CategoryName categoryName);


    @Query(value = "select c.*\n" +
            "from category c\n" +
            "    left join category_novel_relation cnr\n" +
            "        on cnr.novel_id= :novelId and cnr.deleted=false and c.deleted=false\n" +
            "where c.id = cnr.category_id", nativeQuery = true)
    List<Category> findAllCategoryByNovelId(Long novelId);
}
