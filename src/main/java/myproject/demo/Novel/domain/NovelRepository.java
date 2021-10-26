package myproject.demo.Novel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    boolean existsByTitle(Title title);

    Optional<Novel> findByTitle(Title title);

    Optional<Novel> findByIdAndDeleted(Long id, boolean deleted);

    Optional<Novel> findByIdAndDeletedAndAgeGreaterThanEqual(Long id, boolean deleted, Age age);
}
