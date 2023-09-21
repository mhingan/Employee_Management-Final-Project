/**
 * @author Mihaita Hingan
 * MyUserDetails - Custom user details implementation for Spring Security.
 *
 * This class implements the Spring Security `UserDetails` interface to provide custom user details for authentication
 * and authorization.
 */
package com.example.proiectfinal.configuration;

import com.example.proiectfinal.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;


public class MyUserDetails implements UserDetails {

    private final User user;

    /**
     * Constructor that accepts a User object to build UserDetails.
     *
     * @param user The User object containing user details.
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map user roles to GrantedAuthority objects
        return Stream.of(user.getRole())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // User account is always considered non-expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // User account is always considered non-locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // User credentials are always considered non-expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // User account is always considered enabled
        return true;
    }
}
