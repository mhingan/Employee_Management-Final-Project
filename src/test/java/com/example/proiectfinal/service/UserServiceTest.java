package com.example.proiectfinal.service;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.DayOffRequestRepository;
import com.example.proiectfinal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    //TODO: ADD INFO/COMMENTS
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DayOffRequestRepository dayOffRequestRepository;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, dayOffRequestRepository);
    }

    @Test
    public void testFindAll() {
        // Test: Find all users
        // Arrange
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        assertEquals(userList, result);
        System.out.println("Test: Find all: OK");
    }

    @Test
    public void testFindById() {
        // Test: Find a user by id
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertEquals(user, result);
        System.out.println("Test: Find by ID: OK");
    }

    @Test
    public void testUpdate() {
        // Test: Update user
        // Arrange
        User user = new User();
        userService.update(user);

        verify(userRepository, times(1)).save(user);
        System.out.println("Test: Update: OK");
    }

    @Test
    public void testFindByLastName() {
        // Test: Find all users with a specific last name
        // Arrange
        String selectedName = "Smith";
        User user1 = new User();
        user1.setLast_name("Smith");
        User user2 = new User();
        user2.setLast_name("Johnson");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.findByLastName(selectedName);

        verify(userRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals(user1, result.get(0));
        System.out.println("Test: Find by Last Name: OK");
    }


    @Test
    public void testCreateUserWithRoleProvided() {
        // Test: Create user with role specified
        // Arrange
        User user = new User();
        user.setRole("ADMIN"); // Role is provided

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("ADMIN", user.getRole()); // Role should not change
        System.out.println("Test: Create user with Role: OK");
    }

    @Test
    public void testCreateUserWithRoleEmpty() {
        // Test: Create user without specifying the role
        // Arrange
        User user = new User();
        user.setRole(""); // Role is empty

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("USER", user.getRole()); // Role should be set to "USER"
        System.out.println("Test: Create user without Role: OK");
    }


    @Test
    public void testDeleteUser() {
        // Test: Delete user
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(dayOffRequestRepository, times(1)).deleteAll(anyList());
        verify(userRepository, times(1)).delete(user);
        System.out.println("Test: Delete user: OK");
    }

    @Test
    public void testDeleteUserNotFound() {
        // Test: Delete user not found
        // Arrange
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomValidationException.class, () -> userService.deleteUser(userId));
        System.out.println("Test: Delete user not found: OK");
    }

    @Test
    public void testFindAllActiveUsers() {
        // Test: Find all active users, with role = USER
        // Arrange
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setRole("USER");
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAllActiveUsers();

        assertEquals(userList, result);
        System.out.println("Test: Find all active users: OK");
    }

    @Test
    public void testFindCurrentUser() {
        // Test: Find the current user
        // Arrange
        String currentPrincipalName = "user@example.com";
        when(authentication.getName()).thenReturn(currentPrincipalName);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = new User();
        when(userRepository.findByEmail(currentPrincipalName)).thenReturn(Optional.of(user));

        User result = userService.findCurrentUser();

        assertEquals(user, result);
        System.out.println("Test: Find current user: OK");
    }

    @Test
    public void testRequestHolidayWithEnoughDays() {
        // Test: Requesting holiday with enough available days
        // Arrange
        User user = new User();
        user.setHoliday_day(10);
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 5);

        //Act and Assert
        userService.requestHoliday(user, startDate, endDate);

        //Verify
        verify(userRepository, times(1)).save(user);
        assertEquals(5, user.getHoliday_day());
        System.out.println("Test: Request holiday: OK");
    }

    @Test
    public void testRequestHolidayWithNotEnoughDays() {
        // Test: Requesting holiday with not enough available days
        // Arrange
        User user = new User();
        user.setHoliday_day(3); // Set available holiday days
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 5);

        // Act and Assert
        assertThrows(CustomValidationException.class, () -> userService.requestHoliday(user, startDate, endDate));

        // Verify that userRepository.save(user) was not called
        verify(userRepository, never()).save(user);
        System.out.println("Test: Request Holiday with not enough days: OK");
    }

}