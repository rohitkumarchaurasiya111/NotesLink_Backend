package in.noteslink.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Long id;

    //Validation in DTO
    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotBlank(message = "Title cannot be Blank")
    private String title;

    @NotBlank(message = "Material Type cannot be Blank")
    private String type;

    @NotBlank(message = "Drive Link is Required")
    private String driveLink;
    private Boolean isPremium;

    @NotNull(message = "Display Order cannot be Null, You must give some order")
    private Long displayOrder;
}

