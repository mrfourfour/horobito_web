package myproject.demo.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(CategoryName categoryName);

    List<Category> findAllByDeleted(boolean deleted);
}
