package in.noteslink.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectDTO {
    private Long id;

    //Validation in DTO
    @NotNull(message = "College ID is required")
    private Long college_id;
    private String college_name;

    @NotBlank(message = "Subject name is required")
    private String name;
    private String imageURL;

    @NotBlank(message = "Description is Required")
    private String description;

    @NotBlank(message = "Year is required")
    private String year;

    @NotBlank(message = "Branch is required")
    private String branch;
    private Boolean isProject;
}
