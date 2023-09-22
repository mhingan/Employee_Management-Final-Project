package com.example.proiectfinal.service;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.DayOffRequestRepository;
import com.example.proiectfinal.repository.UserRepository;
import com.example.proiectfinal.utils.UserValidation;
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
    private UserValidation userValidation;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, dayOffRequestRepository, userValidation);
    }

    @Test
    public void testFindAll() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        assertEquals(userList, result);
        System.out.println("Test: Find all: OK");
    }

    @Test
    public void testFindById() {

        int userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertEquals(user, result);
        System.out.println("Test: Find by ID: OK");
    }

    @Test
    public void testUpdate() {
        User user = new User();
        userService.update(user);

        verify(userRepository, times(1)).save(user);
        System.out.println("Test: Update: OK");
    }

    @Test
    public void testFindByLastName() {
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
        User user = new User();
        user.setRole("ADMIN"); // Role is provided

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("ADMIN", user.getRole()); // Role should not change
        System.out.println("Test: Create user with Role: OK");
    }

    @Test
    public void testCreateUserWithRoleEmpty() {
        User user = new User();
        user.setRole("");

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("USER", user.getRole());
        System.out.println("Test: Create user without Role: OK");
    }


    @Test
    public void testDeleteUser() {
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
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomValidationException.class, () -> userService.deleteUser(userId));
        System.out.println("Test: Delete user not found: OK");
    }

    @Test
    public void testFindAllActiveUsers() {

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

        User user = new User();
        user.setHoliday_day(10);
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 5);


        userService.requestHoliday(user, startDate, endDate);


        verify(userRepository, times(1)).save(user);
        assertEquals(5, user.getHoliday_day());
        System.out.println("Test: Request holiday: OK");
    }

    @Test
    public void testRequestHolidayWithNotEnoughDays() {

        User user = new User();
        user.setHoliday_day(3);
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 5);


        assertThrows(CustomValidationException.class, () -> userService.requestHoliday(user, startDate, endDate));


        verify(userRepository, never()).save(user);
        System.out.println("Test: Request Holiday with not enough days: OK");
    }

}