package in.noteslink.security;

import in.noteslink.models.entity.NotesLinkUserDetails;
import in.noteslink.service.NotesLinkUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Extending so that this class will behave like a filter
//and will run once per request
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NotesLinkUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //From the client we will get jwt in the Http cookie
        String email = null;            //Username = email (in our case)
        String jwtToken = getJWTFromCookie(request);

        try {
            if(jwtToken != null){
                email = jwtUtil.extractUserEmail(jwtToken);
            }

            // Validate and set authentication, if no authentication has set yet
            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                //Get this user details
                NotesLinkUserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                //Validate the token, if valid then only proceed further
                if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                    //Creates a Spring Security authentication object
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    //Adds request details (like IP address, session ID)
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    //Sets this authentication object into the security context, which means Spring Security now considers this user logged in.
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {          //JWT TOKEN Expired - Throw 401 unauthorized
            // clear cookie
            Cookie cookie = new Cookie("noteslink_token", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired Token");
        }  catch(Exception e){
            // clear cookie
            Cookie cookie = new Cookie("noteslink_token", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired Token");
        }
    }

    //Extracts JWT From Cookie
    private  String getJWTFromCookie(HttpServletRequest request){
        if(request.getCookies() == null) return  null;

        for (Cookie cookie: request.getCookies()){
            if(cookie.getName().equals("noteslink_token")){
                return  cookie.getValue();
            }
        }
        return null;
    }
}