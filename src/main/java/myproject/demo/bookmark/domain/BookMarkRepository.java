package myproject.demo.bookmark.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, BookMarkId> {

    List<BookMark> findAllByNovelIdAndDeleted(Long novelId, boolean deleted);
}
