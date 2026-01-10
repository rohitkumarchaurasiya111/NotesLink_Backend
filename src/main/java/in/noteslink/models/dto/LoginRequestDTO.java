package in.noteslink.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO{

    @NotBlank(message = "idToken is required")
    private String idToken;

}
