package in.noteslink.service;

import in.noteslink.models.dto.LoginResponseDTO;
import in.noteslink.models.entity.User;

public interface UserService {

    // Check if user exists and return the user
    User getUserByEmail(String email);

    // Create a new user with name, email, and college
    User createUser(String name, String email, String emailDomain);

    // Handle login/signup flow (returns JWT token)
    public LoginResponseDTO loginOrSignUpWithGoogle(String idToken);
}
