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
        //Test: Find all accounts requests
        //Arrange
        //creez o noua lista
        List<UserRequest> requestList = new ArrayList<>();
        //cand gaseste toate req acc, mi le returneaza in lista de mai sus
        when(userRequestRepository.findAll()).thenReturn(requestList);

        //creez o lista in acre se aflat toate rezultatele
        List<UserRequest> result = userRequestRepository.findAll();

        //Assert
        assertEquals(requestList, result);
        System.out.println("Test: Find all accounts creation requests: OK");

    }

    @Test
    void findAllCompleted(){
            // Test: Finding all completed user requests
            // Arrange
            List<UserRequest> allRequests = new ArrayList<>();
            UserRequest request1 = new UserRequest();
            request1.setCompleted(true);
            allRequests.add(request1);
            UserRequest request2 = new UserRequest();
            request2.setCompleted(false); // Not completed
            allRequests.add(request2);
            when(userRequestRepository.findAll()).thenReturn(allRequests);

            // Act
            List<UserRequest> result = userRequestService.findAllCompleted();

            // Assert
            assertEquals(1, result.size()); // Only one completed request
            assertEquals(request1, result.get(0));
            System.out.println("Test: Find all completed user requests: OK");

    }


    @Test
    void deleteUserRequest() {
        //Test: delete account request
        //Arrange
        //creez  var requestid, care e id ul requestului
        int reqId = 1;
        //creez un nou obiect de tip UserRequest
        UserRequest userRequest = new UserRequest();
        when(userRequestRepository.findById(reqId)).thenReturn(Optional.of(userRequest));

        userRequestRepository.delete(userRequest);
        //verifc ca repo a sters user request
        verify(userRequestRepository, times(1)).delete(userRequest);
        System.out.println("Test: Delete account request: OK");
    }



    @Test
    public void testSetRequestAsCompleted() {
        // Test: Setting a request as completed
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setCompleted(false); // Not completed initially

        when(userRequestRepository.save(userRequest)).thenReturn(userRequest);

        // Act
        List<UserRequest> completedList = userRequestService.setRequestAsCompleted(userRequest);

        // Assert
        assertTrue(userRequest.isCompleted()); // Request should be marked as completed
        assertEquals(1, completedList.size()); // The completed request should be in the list
        assertEquals(userRequest, completedList.get(0));
        System.out.println("Test: Set request as completed: OK");
    }

}