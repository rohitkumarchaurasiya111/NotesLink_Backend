package in.noteslink.service.impl;

import in.noteslink.mapper.ProjectMapper;
import in.noteslink.models.dto.ProjectDTO;
import in.noteslink.models.entity.Project;
import in.noteslink.models.enums.ProjectDifficulty;
import in.noteslink.repository.ProjectRepository;
import in.noteslink.service.ProjectService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional  //It ensures that all database operations inside the service method either complete successfully together or are rolled back together if something fails.
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /* ================= USER SIDE ================= */

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllActiveProjects() {
        return projectRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDTO> getActiveProjectsByDifficulty(ProjectDifficulty difficulty) {
        return projectRepository
                .findByIsActiveTrueAndDifficultyLevelOrderByDisplayOrderAsc(difficulty)
                .stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO getActiveProjectBySlug(String slug) {
        Project project = projectRepository.findBySlugAndIsActiveTrue(slug)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        return ProjectMapper.toDTO(project);
    }

    /* ================= ADMIN SIDE ================= */

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        Project project = ProjectMapper.toEntity(dto);
        project.setSlug(generateUniqueSlug(dto.getName()));

        try {
            projectRepository.save(project);
        } catch (DataIntegrityViolationException ex) {
            project.setSlug(generateUniqueSlug(dto.getName()));
            projectRepository.save(project);
        }

        return ProjectMapper.toDTO(project);
    }

    /* ================= HELPERS ================= */

    private String generateUniqueSlug(String name) {
        String baseSlug = slugify(name);
        String slug = baseSlug;
        int counter = 1;

        while (projectRepository.existsBySlug(slug)) {
            counter++;
            slug = baseSlug + "-" + counter;
        }
        return slug;
    }

    private String slugify(String input) {
        return input.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
