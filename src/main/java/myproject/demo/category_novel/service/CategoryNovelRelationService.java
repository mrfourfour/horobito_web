package myproject.demo.category_novel.service;


import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.User.service.UserService;
import myproject.demo.category.service.CategoryDto;
import myproject.demo.category.service.CategoryService;
import myproject.demo.category_novel.domain.CategoryNovelRelation;
import myproject.demo.category_novel.domain.CategoryNovelRelationId;
import myproject.demo.category_novel.domain.CategoryNovelRelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryNovelRelationService {
    private final CategoryNovelRelationRepository relationRepository;

    private final UserService userService;

    private final NovelService novelService;

    private final CategoryService categoryService;

    @Transactional
    public void create(Long novelId, List<CategoryDto> categoryDtos) {
        List<CategoryNovelRelation> relations
                = categoryDtos
                .stream()
                .filter(categoryDto->
                        !relationRepository.existsById(CategoryNovelRelationId.create(categoryDto.getCategoryId(), novelId)))
                .map(categoryDto -> CategoryNovelRelation.create(categoryDto.getCategoryId(), novelId)).collect(Collectors.toList());
        relationRepository.saveAllAndFlush(relations);
    }

    @Transactional
    public void delete(Long novelId, Long categoryId){
        Optional<CategoryNovelRelation> relation = relationRepository.findById(CategoryNovelRelationId.create(categoryId, novelId));
        checkExistence(relation);
        checkAlreadyDeleted(relation.get());
        relation.get().delete();
    }

    @Transactional
    public void update(Long novelId, List<CategoryDto> categoryDtos) {
        relationRepository.deleteCategoryNovelRelationsByNovelId(novelId);
        create(novelId, categoryDtos);
    }

    public List<Long> findByCategoryIds(List<Long> categoryIds){
        return relationRepository.findByCategoryIdIn(categoryIds).stream()
        .filter(Optional::isPresent)
        .map(categoryNovelRelation -> categoryNovelRelation.get().getNovelId())
        .collect(Collectors.toList());
    }

    public List<Long> findByNovelIds(List<Long> categoryIds){
        return relationRepository.findByCategoryIdIn(categoryIds).stream()
                .filter(Optional::isPresent)
                .map(categoryNovelRelation -> categoryNovelRelation.get().getNovelId())
                .collect(Collectors.toList());
    }



    private void checkAlreadyDeleted(CategoryNovelRelation relation) {
        if (relation.checkDeleted()){
            throw new IllegalArgumentException();
        }
    }


    private void checkExistence(Optional<CategoryNovelRelation> relation) {
        if (relation.isEmpty()){
            throw new IllegalArgumentException();
        }
    }

    public List<Long> findCategoryIdsByNovelId(Long novelId) {
        return relationRepository
                .findAllByNovelIdAndDeleted(novelId, false).stream()
                .map(CategoryNovelRelation::getCategoryId).collect(Collectors.toList());
    }
}
