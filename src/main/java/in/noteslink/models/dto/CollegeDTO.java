package in.noteslink.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTOs are used between backend → frontend (API responses) so that you do not expose your database entities directly.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeDTO {
    private  Long id;
    private String name;
    private String subdomain;
    private String emailDomain;
    private String logoURL;
}
