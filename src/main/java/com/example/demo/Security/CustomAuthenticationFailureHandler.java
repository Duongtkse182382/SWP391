package com.example.demo.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String errorMessage = null;

        if (exception instanceof UsernameNotFoundException || exception.getMessage().equalsIgnoreCase("Email is not valid")) {
            // Email not found in database or invalid email
            errorMessage = "Email is not valid";
            request.getSession().setAttribute("emailError", errorMessage);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=email");
        } else if (exception.getMessage().equalsIgnoreCase("Account is not active")) {
            // Account is not active
            errorMessage = "Account is not active";
            request.getSession().setAttribute("accountError", errorMessage);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=account");
        } else {
            // Let Spring Security handle password-related errors
            errorMessage = "Password is wrong";
            request.getSession().setAttribute("passwordError", errorMessage);
            getRedirectStrategy().sendRedirect(request, response, "/login?error=EmailOrPassword");
        }
    }
}