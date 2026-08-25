package in.noteslink.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;


import in.noteslink.exception.BadRequestException;
import in.noteslink.models.dto.LoginResponseDTO;
import in.noteslink.models.entity.User;
import in.noteslink.models.entity.College;
import in.noteslink.models.enums.UserRole;
import in.noteslink.repository.UserRepository;
import in.noteslink.repository.CollegeRepository;
import in.noteslink.security.JwtUtil;
import in.noteslink.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    private static final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    @Value("${google.client.id}")
    private String googleClientId;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User createUser(String name, String email, String emailDomain) {
        Optional<College> collegeOpt = collegeRepository.findByEmailDomain(emailDomain.toLowerCase());

        if (collegeOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login with your college email id");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setCollege(collegeOpt.get());
        user.setRole(UserRole.PREMIUM);             //Change this to FREE after Lunching Premium Subscription

        return userRepository.save(user);
    }

    @Override
    public LoginResponseDTO loginOrSignUpWithGoogle(String idTokenString) {

        if (idTokenString == null || idTokenString.isBlank()) {
            throw new BadRequestException("Google ID token is required");
        }

        GoogleIdToken.Payload payload = verifyGoogleToken(idTokenString);

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        // Extract domain after @
        String emailDomain = email.substring(email.indexOf("@") + 1).toLowerCase();

        // Check if user exists
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseGet(() -> createUser(name, email, emailDomain));

        // Generate JWT token for this user
        String token = jwtUtil.generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .collegeId(user.getCollege().getId())
                .collegeLogo(user.getCollege().getLogoURL())
                .build();
    }

    /**
     * Verifies the Google ID token and returns its payload.
     */
    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();


            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Google ID token");
            }

            return idToken.getPayload();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Google token verification failed: " + e.getMessage());
        }
    }
}
