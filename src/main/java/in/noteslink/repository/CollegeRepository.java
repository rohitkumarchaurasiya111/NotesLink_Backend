package in.noteslink.repository;

import in.noteslink.models.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {
    Optional<College> findBySubdomain(String subdomain);
    Optional<College> findByEmailDomain(String emailDomain);

//    These both methods are already present in the JpaRepository<College, Long>. So, no need to Redeclare them.
//    List<College> findAll();
//    Optional<College> findById(Long CollegeId);
}
