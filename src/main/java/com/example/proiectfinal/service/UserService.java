/**
 * Service class for managing user-related operations.
 * This service provides methods for handling user data, including user CRUD operations,
 * holiday request validation, and password change functionality.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.service;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.DayOffRequest;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.DayOffRequestRepository;
import com.example.proiectfinal.repository.UserRepository;
import com.example.proiectfinal.utils.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserValidation userValidation;
    private final UserRepository userRepository;

    private final DayOffRequestRepository dayOffRequestRepository;


    @Autowired
    public UserService(UserRepository userRepository, DayOffRequestRepository dayOffRequestRepository, UserValidation userValidation) {
        this.userRepository = userRepository;
        this.dayOffRequestRepository = dayOffRequestRepository;
        this.userValidation = userValidation;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of all users.
     */
    public List<User> findAll() {
        logger.info("Finding all users...");
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws RuntimeException if no user is found with the given ID.
     */
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No product found with id: " + id));
    }

    /**
     * Updates a user's information.
     *
     * @param user The user object with updated information.
     */
    public void update(User user) {
        userRepository.save(user);
    }

    /**
     * Retrieves a list of users with a specific last name.
     *
     * @param selectedName The last name to filter by.
     * @return List of users with the specified last name.
     */
    public List<User> findByLastName(String selectedName) {
        return userRepository.findAll()
                .stream()
                .filter(user -> selectedName.equals(user.getLast_name()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user.
     *
     * @param user The user object to create.
     */
    public void createUser(User user) {
        userValidation.validateNewUser(user);
        if (user.getRole().isEmpty()) {
            user.setRole("USER");
        } else {
            user.setRole(user.getRole());
        }
        userRepository.save(user);
    }

    /**
     * Delete a user by their unique ID.
     *
     * @param userId The ID of the user to delete.
     * @throws CustomValidationException if the user is not found with the given ID.
     */
    public void deleteUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            List<DayOffRequest> userRequests = dayOffRequestRepository.findByUserId(userId);
            dayOffRequestRepository.deleteAll(userRequests);

            userRepository.delete(user);
        } else {
            throw new CustomValidationException("User not found with ID: " + userId);
        }
    }

    /**
     * Retrieves a list of all active users.
     *
     * @return List of active users.
     */
    public List<User> findAllActiveUsers() {
        List<User> all = userRepository.findAll();
        List<User> active = new ArrayList<>();
        for (User user : all) {
            if (user.getRole().equals("USER")) {
                active.add(user);
            }
        }

        return active;
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The currently authenticated user.
     */
    public User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return (User) userRepository.findByEmail(currentPrincipalName).orElse(null);
    }


    /**
     * Handles a user's holiday request and deducts the requested days from their available holiday days.
     *
     * @param user      The user requesting a holiday.
     * @param startDate The start date of the requested holiday.
     * @param endDate   The end date of the requested holiday.
     * @throws CustomValidationException if the user doesn't have enough available holiday days.
     */
    public void requestHoliday(User user, LocalDate startDate, LocalDate endDate) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (user.getHoliday_day() >= numberOfDays) {
            int newHolidayNumber = user.getHoliday_day() - (int) numberOfDays;
            user.setHoliday_day(newHolidayNumber);
            userRepository.save(user);
        } else {
            throw new CustomValidationException("You don't have enough days, select a smaller interval of time.");
        }
    }

    //todo - change password
    /**
     * Changes a user's password.
     *
     * @param userId   The ID of the user whose password is being changed.
     * @param password The new password.
     * @throws CustomValidationException if the user is not found with the given ID.
     */
    public void changePassword(int userId, String password) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setHashedPassword(hashedPassword);
            userRepository.save(user);
        } else {
            throw new CustomValidationException("failed err: 4065");
        }

    }
}
