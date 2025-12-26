package in.noteslink.repository;

import in.noteslink.models.entity.Material;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findBySubjectId(Long subjectId);
}
