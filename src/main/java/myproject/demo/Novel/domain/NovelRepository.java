package myproject.demo.Novel.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    boolean existsByTitle(Title title);
}
