package in.noteslink.config;

import in.noteslink.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Making Our Own Security Chain
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    //SecurityFilterChain is essentially a list of servlet filters that intercept incoming HTTP requests and apply various security checks before allowing the request to reach your controllers.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.disable())       //Not required in JWT Based AUthentication (because JWT is stateless)
                .cors(Customizer.withDefaults())                                         //To allow cross origin
                .authorizeHttpRequests(requests -> requests         //This Tells that any HTTP request on the server needs to be authenticated
                        .requestMatchers("/api/auth/loginwithgoogle", "/api/users/colleges", "/api/health").permitAll()     //Will not authenticate these URLs generally used for Login and Register URLs
                        // Admin endpoints
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated());

//        http.httpBasic(Customizer.withDefaults());

        //Spring Security will not create or use an HTTP session to store the authentication state.
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Telling to run jwtFilter before running UsernamePasswordAuthenticationFilter
        //This is done to verify jwtToken
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }
}
