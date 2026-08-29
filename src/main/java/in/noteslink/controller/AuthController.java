package in.noteslink.controller;

import in.noteslink.models.dto.LoginRequestDTO;
import in.noteslink.models.dto.LoginResponseDTO;
import in.noteslink.models.entity.User;
import in.noteslink.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // User
    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getUserDeatils(Authentication authentication){
        String email = authentication.getName();            //UserName = email, in our case
        User user = userService.getUserByEmail(email);

        LoginResponseDTO loginResponseDTO =  LoginResponseDTO.builder()
                .name(user.getName())
                .role(user.getRole().name())
                .email(email)
                .collegeId(user.getCollege().getId())
                .collegeLogo(user.getCollege().getLogoURL()).build();
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/loginwithgoogle")
    public ResponseEntity<LoginResponseDTO> loginWithGoogle(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse response) {
        // UserService now handles all exceptions (invalid token, unauthorized email, etc.)
        LoginResponseDTO loginResponseDTO = userService.loginOrSignUpWithGoogle(request.getIdToken());

        //Jwt Token securely Storing in the HTTP Cookie
        ResponseCookie cookie = ResponseCookie.from("noteslink_token", loginResponseDTO.getToken())
                .httpOnly(true)           //HttpOnly doesn't let Javascript see the value
                .secure(true)             //If true, Https can only be used
                .sameSite("None")           //Don't need same domain to call the API's
                .path("/")               //Cookie will be available in all routes
                .maxAge(7*24*60*60)       //Life Time of 7 days
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());         //Adding the cookie in response

        loginResponseDTO.setToken("Calm Down, Sweetheart");
        return ResponseEntity.ok(loginResponseDTO);
    }
}
