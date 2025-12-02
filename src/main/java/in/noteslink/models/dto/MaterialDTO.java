package in.noteslink.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {
    private Long id;
    private Long subjectId;
    private String title;
    private String type;
    private String driveLink;
    private Boolean isPremium;
}

