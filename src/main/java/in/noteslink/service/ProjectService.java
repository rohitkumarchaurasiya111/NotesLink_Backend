package in.noteslink.service;

import in.noteslink.models.dto.ProjectDTO;
import in.noteslink.models.enums.ProjectDifficulty;

import java.util.List;

public interface ProjectService {
    // User-side
    List<ProjectDTO> getAllActiveProjects();
    List<ProjectDTO> getActiveProjectsByDifficulty(ProjectDifficulty difficulty);
    ProjectDTO getActiveProjectBySlug(String slug);

    // Admin-side
    List<ProjectDTO> getAllProjectsEitherActiveOrInActive();
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO, Long id);
}
