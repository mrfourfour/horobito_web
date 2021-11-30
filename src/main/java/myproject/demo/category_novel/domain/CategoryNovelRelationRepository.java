package myproject.demo.category_novel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryNovelRelationRepository extends JpaRepository<CategoryNovelRelation, CategoryNovelRelationId> {


    List<CategoryNovelRelation> findAllByCategoryId(Long id);

    List<Optional<CategoryNovelRelation>> findByCategoryIdIn(List<Long> categoryIds );

    List<Optional<CategoryNovelRelation>> findByNovelIdIn(List<Long> novelIds );

    List<CategoryNovelRelation> findAllByNovelIdAndDeleted(Long id, boolean deleted);

    List<CategoryNovelRelation> findAllByNovelId(Long novelId);

    List<CategoryNovelRelation> findAllByCategoryIdAndDeleted(Long categoryId, boolean deleted);
}
