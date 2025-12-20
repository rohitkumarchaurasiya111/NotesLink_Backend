package in.noteslink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    //SecurityFilterChain is essentially a list of servlet filters that intercept incoming HTTP requests and apply various security checks before allowing the request to reach your controllers.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.disable())       //Not required in JWT Based AUthentication
                .cors(Customizer.withDefaults())                                         //To allow cross origin
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()     //Will not authenticate these URLs generally used for Login and Register URLs
                        .anyRequest().permitAll());

        return http.build();
    }
}
