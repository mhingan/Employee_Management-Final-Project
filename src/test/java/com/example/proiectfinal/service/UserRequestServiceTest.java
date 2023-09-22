package com.example.proiectfinal.service;

import com.example.proiectfinal.model.UserRequest;
import com.example.proiectfinal.repository.UserRequestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRequestServiceTest {
    //TODO: ADD INFO/COMMENTS
    private UserRequestService userRequestService;

    @Mock
    private UserRequestRepository userRequestRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRequestService = new UserRequestService(userRequestRepository);
    }


    @Test
    void findAll() {

        List<UserRequest> requestList = new ArrayList<>();
        when(userRequestRepository.findAll()).thenReturn(requestList);

        List<UserRequest> result = userRequestRepository.findAll();

        assertEquals(requestList, result);
        System.out.println("Test: Find all accounts creation requests: OK");

    }

    @Test
    void findAllCompleted(){

            List<UserRequest> allRequests = new ArrayList<>();
            UserRequest request1 = new UserRequest();
            request1.setCompleted(true);
            allRequests.add(request1);
            UserRequest request2 = new UserRequest();
            request2.setCompleted(false);
            allRequests.add(request2);
            when(userRequestRepository.findAll()).thenReturn(allRequests);


            List<UserRequest> result = userRequestService.findAllCompleted();

            assertEquals(1, result.size());
            assertEquals(request1, result.get(0));
            System.out.println("Test: Find all completed user requests: OK");

    }


    @Test
    void deleteUserRequest() {

        int reqId = 1;

        UserRequest userRequest = new UserRequest();
        when(userRequestRepository.findById(reqId)).thenReturn(Optional.of(userRequest));

        userRequestRepository.delete(userRequest);

        verify(userRequestRepository, times(1)).delete(userRequest);
        System.out.println("Test: Delete account request: OK");
    }



    @Test
    public void testSetRequestAsCompleted() {

        UserRequest userRequest = new UserRequest();
        userRequest.setCompleted(false);

        when(userRequestRepository.save(userRequest)).thenReturn(userRequest);


        List<UserRequest> completedList = userRequestService.setRequestAsCompleted(userRequest);


        assertTrue(userRequest.isCompleted());
        assertEquals(1, completedList.size());
        assertEquals(userRequest, completedList.get(0));
        System.out.println("Test: Set request as completed: OK");
    }

}