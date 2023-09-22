/** @author Mihaita Hingan*/
package com.example.proiectfinal.configuration;


import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * DbUserDetailsService - Custom user details service for loading user information from the database.

 * This service class is used to implement the Spring Security `UserDetailsService` interface, providing custom logic
 * for loading user details based on their username (in this case, email) from the database.
 */

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user details by username (email) from the database.
     * @param username The username (email) of the user to load.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDatabase = (User) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database with this name"));
        return new MyUserDetails(userFromDatabase);
    }
}
