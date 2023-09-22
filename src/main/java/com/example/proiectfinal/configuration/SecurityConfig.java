/**
 * @author Mihaita Hingan
 * SecurityConfig - Configuration class for Spring Security.
 * This class defines the security configuration for the application, including security filter chains, authorization
 * rules, and password encoding.
 */

package com.example.proiectfinal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain with authorization rules.
     *
     * @param http The HttpSecurity object to configure security.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/", "/signup", "/company").permitAll()
                        .requestMatchers("/admin", "/admin/accounts/requests", "/admin/accounts/requests/completed/{id}",
                                "/admin/accounts/requests/history", "/admin/accounts/requests/{id}",
                                "/admin/accounts/requests/{id}", "/admin/employees", "/admin/employees/create",
                                "/admin/employees/delete/{id}", "/admin/employees/edit/{id}", "/admin/panel",
                                "/admin/search-by-last_name", "/admin/statistics").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    /**
     * Creates a PasswordEncoder bean for encoding and decoding passwords.
     *
     * @return A PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
