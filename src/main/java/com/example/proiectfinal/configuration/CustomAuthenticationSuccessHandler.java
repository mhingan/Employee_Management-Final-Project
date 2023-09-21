/** @author Mihaita Hingan*/
package com.example.proiectfinal.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /**
     * CustomAuthenticationSuccessHandler - Custom authentication success handler for redirecting users after login based on the Role.
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> "USER".equals(authority.getAuthority()))) {
            return "/dashboard";
        } else {
            return "/admin/panel";
        }
    }

}
