package in.noteslink.repository;

import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Years;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByYear(Years year);
}
