package in.noteslink.models.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDTO {

    private String token;
    private String message;

    public LoginResponseDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

}
