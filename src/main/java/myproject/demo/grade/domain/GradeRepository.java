package myproject.demo.grade.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
     List<Grade> findAllByPremium_PremiumAndDeleted(boolean premium, boolean deleted);


    List<Grade> findAllByDeleted(boolean deleted);
}
