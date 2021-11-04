package myproject.demo.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(CategoryName categoryName);

    List<Category> findAllByDeleted(boolean deleted);

    Optional<Category> findByCategoryName_CategoryNameAndDeleted(String categoryNames, boolean deleted);

    Optional<Category> findByIdAndDeleted(Long id, boolean deleted);
}
