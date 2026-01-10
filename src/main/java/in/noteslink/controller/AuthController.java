package in.noteslink.controller;

import in.noteslink.models.dto.LoginRequestDTO;
import in.noteslink.models.dto.LoginResponseDTO;
import in.noteslink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginWithGoogle(@Valid @RequestBody LoginRequestDTO request) {
        // UserService now handles all exceptions (invalid token, unauthorized email, etc.)
        String token = userService.loginOrSignUpWithGoogle(request.getIdToken());

        return ResponseEntity.ok(new LoginResponseDTO(token, "Login successful"));
    }
}
