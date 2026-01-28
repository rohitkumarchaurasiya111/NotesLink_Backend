package in.noteslink.service.impl;

import in.noteslink.exception.BadRequestException;
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
    public List<ProjectDTO> getAllProjectsEitherActiveOrInActive() {
        return projectRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(ProjectMapper::toDTO)
                .toList();
    }

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {
        Project project = ProjectMapper.toEntity(dto);
        project.setSlug(generateUniqueSlug(dto.getName()));

        try {
            project = projectRepository.save(project);
        } catch (DataIntegrityViolationException ex) {
            project.setSlug(generateUniqueSlug(dto.getName()));
            project = projectRepository.save(project);
        }
        return ProjectMapper.toDTO(project);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO, Long id) {
        //Getting the Current Project Details From DB
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Modify the managed entity
        existing.setName(projectDTO.getName());
        existing.setDescription(projectDTO.getDescription());
        existing.setImageURL(projectDTO.getImageURL());
        existing.setTechStacksUsed(projectDTO.getTechStacksUsed());
        existing.setDifficultyLevel(projectDTO.getDifficultyLevel());
        existing.setDeployedLink(projectDTO.getDeployedLink());
        existing.setGithubLink(projectDTO.getGithubLink());
        existing.setDisplayOrder(projectDTO.getDisplayOrder());
        existing.setIsActive(projectDTO.getIsActive());

        existing.setSlug(generateUniqueSlug(projectDTO.getName()));

        Project updatedProject = null;
        try {
            updatedProject = projectRepository.save(existing);
        }catch(Exception e){
            throw new BadRequestException("Erro while Updating Project");
        }
        return ProjectMapper.toDTO(updatedProject);
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
