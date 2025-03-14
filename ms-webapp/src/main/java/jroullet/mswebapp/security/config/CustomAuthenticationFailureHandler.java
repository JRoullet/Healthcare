package jroullet.mswebapp.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid username or password";
        if(exception instanceof BadCredentialsException){
            errorMessage = "Invalid username or password";
        }
        else if(exception instanceof UsernameNotFoundException){
            errorMessage = "Username not found";
        }
        else if(exception instanceof DisabledException){
            errorMessage = "User is disabled";
        }

        response.sendRedirect("/signin?authError=" + URLEncoder.encode(errorMessage, "UTF-8"));

    }
}
