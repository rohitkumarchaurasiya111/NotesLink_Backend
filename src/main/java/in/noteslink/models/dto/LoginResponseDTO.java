package in.noteslink.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class LoginResponseDTO {
    private String token;        // Sending Token as HttpOnly Cookie as it is more secure
    private String email;
    private String name;
    private String role;
    private Long collegeId;
    private String collegeLogo;
}
