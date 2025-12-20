package in.noteslink.repository;

import in.noteslink.models.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {
    Optional<College> findById(Long CollegeId);
    Optional<College> findBySubdomain(String subdomain);
}
