package com.example.proiectfinal.service;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.DayOffRequest;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.DayOffRequestRepository;
import com.example.proiectfinal.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DayOffRequestServiceTest {
    //TODO: ADD INFO/COMMENTS
    private DayOffRequestService dayOffRequestService;

    @Mock
    private DayOffRequestRepository dayOffRequestRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dayOffRequestService = new DayOffRequestService(dayOffRequestRepository, userRepository);
    }


    @Test
    void allDaysOff() {
        // Test: Find all day off requests
        // Arrange

        // Create a list of sample DayOffRequest objects
        List<DayOffRequest> requests = new ArrayList<>();
        // Add sample DayOffRequest objects to the list

        // Mock the behavior of dayOffRequestRepository.findAll()
        when(dayOffRequestRepository.findAll()).thenReturn(requests);

        // Act
        List<DayOffRequest> dayOffRequests = dayOffRequestService.allDaysOff();

        // Assert
        assertEquals(requests, dayOffRequests);
        System.out.println("Test: Find all holiday requests: OK");
    }

    @Test
    void getById() {
        // Test: Get a day off request by ID
        // Arrange

        // Create a sample day off request ID
        int requestId = 1;

        // Create a mock DayOffRequest object for dayOffRequestRepository.findById
        DayOffRequest mockRequest = new DayOffRequest();
        when(dayOffRequestRepository.findById(requestId)).thenReturn(Optional.of(mockRequest));

        // Act
        DayOffRequest result = dayOffRequestService.getById(requestId);

        // Assert

        // Verify that dayOffRequestRepository.findById was called with the correct ID
        verify(dayOffRequestRepository, times(1)).findById(requestId);

        // Check if the returned day off request matches the mockRequest
        assertEquals(mockRequest, result);
        System.out.println("Test: Find holiday by id: OK");
    }

    @Test
    void addNewDaysOff() {
        // Test: Add a new day off request
        // Arrange

        // Create a mock DayOffRequest object and a mock User object
        DayOffRequest mockDayOffRequest = mock(DayOffRequest.class); // Create a mock
        User mockUser = mock(User.class); // Create a mock

        // Act
        dayOffRequestService.addNewDaysOff(mockDayOffRequest, mockUser);

        // Assert

        // Verify that dayOffRequest.setUser was called with the mockUser
        verify(mockDayOffRequest, times(1)).setUser(mockUser);

        // Verify that dayOffRequestRepository.save was called with the mockDayOffRequest
        verify(dayOffRequestRepository, times(1)).save(mockDayOffRequest);
        System.out.println("Test: Add new day off request: OK");
    }

    @Test
    void cancelRequest() {

            // Test: Cancel a day off request
            // Arrange

            // Create a mock DayOffRequest object and a mock User object
            DayOffRequest mockDayOffRequest = mock(DayOffRequest.class); // Create a mock
            User mockUser = mock(User.class); // Create a mock

            // Configure the mock objects
            when(mockDayOffRequest.isCanceled()).thenReturn(false);
            when(mockDayOffRequest.getStartDate()).thenReturn(LocalDate.now().plusDays(1));
            when(mockDayOffRequest.getNumberOfRequestedDays()).thenReturn(3);
            when(mockUser.getHoliday_day()).thenReturn(5);

            // Act
            dayOffRequestService.cancelRequest(mockDayOffRequest, mockUser);

            // Assert

            // Verify that dayOffRequest.setCanceled was called with true
            verify(mockDayOffRequest, times(1)).setCanceled(true);

            // Verify that user.setHoliday_day was called with the updated holiday days
            verify(mockUser, times(1)).setHoliday_day(8); // 5 + 3

            // Verify that dayOffRequestRepository.save was called with the mockDayOffRequest
            verify(dayOffRequestRepository, times(1)).save(mockDayOffRequest);
        System.out.println("Test: Cancel day off request: OK");
        }

    @Test
    void cancelRequestAfterStartDate() {
        // Test: Cancel a day off request after the start date
        // Arrange

        // Create a mock DayOffRequest object and a mock User object
        DayOffRequest mockDayOffRequest = mock(DayOffRequest.class); // Create a mock
        User mockUser = mock(User.class); // Create a mock

        // Configure the mock objects
        when(mockDayOffRequest.isCanceled()).thenReturn(false);
        when(mockDayOffRequest.getStartDate()).thenReturn(LocalDate.now().minusDays(1)); // Set start date in the past
        when(mockDayOffRequest.getNumberOfRequestedDays()).thenReturn(3);
        when(mockUser.getHoliday_day()).thenReturn(5);

        // Act and Assert

        // Verify that cancelRequest throws a CustomValidationException when the start date is in the past
        assertThrows(CustomValidationException.class, () -> dayOffRequestService.cancelRequest(mockDayOffRequest, mockUser));

        // Verify that dayOffRequest.setCanceled and user.setHoliday_day were not called
        verify(mockDayOffRequest, never()).setCanceled(true);
        verify(mockUser, never()).setHoliday_day(anyInt());

        // Verify that dayOffRequestRepository.save was not called
        verify(dayOffRequestRepository, never()).save(mockDayOffRequest);

        System.out.println("Test: Cancel day off request after start date: OK");
    }

}