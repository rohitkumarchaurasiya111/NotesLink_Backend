package in.noteslink.mapper;

import in.noteslink.models.dto.ProjectDTO;
import in.noteslink.models.entity.Project;

public class ProjectMapper {

    private ProjectMapper() {
        // utility class
    }

    public static ProjectDTO toDTO(Project project) {
        if (project == null) return null;

        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .slug(project.getSlug())
                .imageURL(project.getImageURL())
                .description(project.getDescription())
                .techStacksUsed(project.getTechStacksUsed())
                .deployedLink(project.getDeployedLink())
                .githubLink(project.getGithubLink())
                .difficultyLevel(project.getDifficultyLevel())
                .displayOrder(project.getDisplayOrder())
                .isActive(project.getIsActive())
                .build();
    }

    public static Project toEntity(ProjectDTO dto) {
        if (dto == null) return null;

        return Project.builder()
                .name(dto.getName())
                .imageURL(dto.getImageURL())
                .description(dto.getDescription())
                .techStacksUsed(dto.getTechStacksUsed())
                .deployedLink(dto.getDeployedLink())
                .githubLink(dto.getGithubLink())
                .difficultyLevel(dto.getDifficultyLevel())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();
    }
}

