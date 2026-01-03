package in.noteslink.repository;

import in.noteslink.models.entity.College;
import in.noteslink.models.entity.DriveFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriveFolderRepository extends JpaRepository<DriveFolder, Long> {
    Optional<DriveFolder> findByCollegeAndName(College college, String name);
    Optional<DriveFolder> findByCollege_IdAndName(Long collegeId, String name);
}
