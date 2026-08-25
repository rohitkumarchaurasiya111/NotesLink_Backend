package in.noteslink.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.noteslink.models.entity.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    //Extracting the values from application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    //Generates SecretKey for each user
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //Generating the Token
    //Notes: we are using user email as the user identifier
    public String generateToken(User user) {
        //To put additional data inside JWT payload (like roles, permissions)
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
//        claims.put("collegeId", user.getCollege().getId());       -> Passing the CollegId as a Payload

        return Jwts.builder()
                .setClaims(claims)      //Adding the map of claims which we created above
                .setSubject(String.valueOf(user.getEmail()))       //user identifier, Using user email as it's identifier
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)        //Signs the token with your secret key
                .compact();             //Converts everything into a compact JWT string
    }

    // Generic method to extract all claim from token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Gives user email of this user
    public String extractUserEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Gives Role of current user
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    //Check Expiration
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    //Validate Token
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractUserEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
}
