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
    //todo: Inject mock
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


        List<DayOffRequest> requests = new ArrayList<>();

        when(dayOffRequestRepository.findAll()).thenReturn(requests);

        List<DayOffRequest> dayOffRequests = dayOffRequestService.allDaysOff();

        assertEquals(requests, dayOffRequests);
        System.out.println("Test: Find all holiday requests: OK");
    }

    @Test
    void getById() {


        int requestId = 1;

        DayOffRequest mockRequest = new DayOffRequest();
        when(dayOffRequestRepository.findById(requestId)).thenReturn(Optional.of(mockRequest));

        DayOffRequest result = dayOffRequestService.getById(requestId);

        // Assert
        verify(dayOffRequestRepository, times(1)).findById(requestId);

        assertEquals(mockRequest, result);
        System.out.println("Test: Find holiday by id: OK");
    }

    @Test
    void addNewDaysOff() {

        DayOffRequest mockDayOffRequest = mock(DayOffRequest.class); // Create a mock
        User mockUser = mock(User.class); // Create a mock

        dayOffRequestService.addNewDaysOff(mockDayOffRequest, mockUser);

        verify(mockDayOffRequest, times(1)).setUser(mockUser);

        verify(dayOffRequestRepository, times(1)).save(mockDayOffRequest);
        System.out.println("Test: Add new day off request: OK");
    }

    @Test
    void cancelRequest() {



            DayOffRequest mockDayOffRequest = mock(DayOffRequest.class);
            User mockUser = mock(User.class);

            when(mockDayOffRequest.isCanceled()).thenReturn(false);
            when(mockDayOffRequest.getStartDate()).thenReturn(LocalDate.now().plusDays(1));
            when(mockDayOffRequest.getNumberOfRequestedDays()).thenReturn(3);
            when(mockUser.getHoliday_day()).thenReturn(5);

            dayOffRequestService.cancelRequest(mockDayOffRequest, mockUser);

            verify(mockDayOffRequest, times(1)).setCanceled(true);

            verify(mockUser, times(1)).setHoliday_day(8); // 5 + 3

            verify(dayOffRequestRepository, times(1)).save(mockDayOffRequest);
        System.out.println("Test: Cancel day off request: OK");
        }

    @Test
    void cancelRequestAfterStartDate() {

        DayOffRequest mockDayOffRequest = mock(DayOffRequest.class);
        User mockUser = mock(User.class);

        when(mockDayOffRequest.isCanceled()).thenReturn(false);
        when(mockDayOffRequest.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(mockDayOffRequest.getNumberOfRequestedDays()).thenReturn(3);
        when(mockUser.getHoliday_day()).thenReturn(5);

        assertThrows(CustomValidationException.class, () -> dayOffRequestService.cancelRequest(mockDayOffRequest, mockUser));

        verify(mockDayOffRequest, never()).setCanceled(true);
        verify(mockUser, never()).setHoliday_day(anyInt());

        verify(dayOffRequestRepository, never()).save(mockDayOffRequest);

        System.out.println("Test: Cancel day off request after start date: OK");
    }

}