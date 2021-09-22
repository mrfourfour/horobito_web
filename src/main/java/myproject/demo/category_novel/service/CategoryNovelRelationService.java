package myproject.demo.category_novel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelation;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryNovelRelationService {
    private final CategoryNovelRelationRepository relationRepository;

    private final UserService userService;

    private final NovelService novelService;

    private final CategoryService categoryService;

    @Transactional
    public void create(Long novelId, List<Long> categoryIds) {
        List<CategoryNovelRelation> relations
                = categoryIds
                .stream()
                .filter(categoryId ->
                        !relationRepository.existsById(CategoryNovelRelationId.create(categoryId, novelId)))
                .map(categoryId -> CategoryNovelRelation.create(categoryId, novelId)).collect(Collectors.toList());
        relationRepository.saveAll(relations);
    }



}
