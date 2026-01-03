package in.noteslink.models.dto;

import in.noteslink.models.enums.ProjectDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Project name cannot be blank")
    @Size(max = 255, message = "Project name cannot exceed 255 characters")
    private String name;

    private String slug;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageURL;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Tech stacks used cannot be blank")
    @Size(max = 500, message = "Tech stacks cannot exceed 500 characters")
    private String techStacksUsed;

    @Size(max = 500, message = "Deployed link cannot exceed 500 characters")
    private String deployedLink;

    @Size(max = 500, message = "GitHub link cannot exceed 500 characters")
    private String githubLink;

    @NotNull(message = "Project difficulty level is required")
    private ProjectDifficulty difficultyLevel;

    @NotNull(message = "Display order is required")
    private Long displayOrder;

    private Boolean isActive;
}
