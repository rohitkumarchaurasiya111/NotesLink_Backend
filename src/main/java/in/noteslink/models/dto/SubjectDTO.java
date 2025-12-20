package in.noteslink.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long id;
    private Long college_id;
    private String college_name;
    private String name;
    private String imageURL;
    private String description;
    private String year;
    private String branch;
}
