/**
 * @author Mihaita Hingan
 * MyUserDetails - Custom user details implementation for Spring Security.
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
