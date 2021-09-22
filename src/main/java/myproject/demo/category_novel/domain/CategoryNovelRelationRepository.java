package myproject.demo.category_novel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryNovelRelationRepository extends JpaRepository<CategoryNovelRelation, CategoryNovelRelationId> {


    List<CategoryNovelRelation> findAllByCategoryId(Long id);

    List<CategoryNovelRelation> findAllByNovelId(Long id);

}
