package in.noteslink.repository;

import in.noteslink.models.entity.Project;
import in.noteslink.models.enums.ProjectDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    /**
     * Fetch all active projects ordered by displayOrder
     * (Used for user-facing APIs)
     */
    List<Project> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Fetch active projects filtered by difficulty
     */
    List<Project> findByIsActiveTrueAndDifficultyLevelOrderByDisplayOrderAsc(
            ProjectDifficulty difficultyLevel
    );

    /**
     * Fetch a single active project by slug
     * (For /projects/{slug} page)
     */
    Optional<Project> findBySlugAndIsActiveTrue(String slug);

    /**
     * Admin use-case: fetch all projects (active + inactive)
     */
    List<Project> findAllByOrderByDisplayOrderAsc();

    /**
     * Optional: check slug uniqueness before save
     */
    boolean existsBySlug(String slug);
}
