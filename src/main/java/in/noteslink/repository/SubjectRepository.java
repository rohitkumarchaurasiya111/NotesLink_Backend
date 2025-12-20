package in.noteslink.repository;

import in.noteslink.models.entity.College;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Years;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(Long SubjectId);
    List<Subject> findByYear(Years year);

    /**
     * Find visible subjects filtered by year for a college (global + college specific).
     */
    @Query("""
           SELECT s FROM Subject s
           WHERE (s.college IS NULL OR s.college.id = :collegeId)
           AND s.year = :year
           ORDER BY s.name
           """)
    List<Subject> findForCollegeByYear(@Param("collegeId") Long collegeId,
                                       @Param("year") Years year);
}
