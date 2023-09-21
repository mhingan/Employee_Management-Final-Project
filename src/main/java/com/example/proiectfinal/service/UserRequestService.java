/**
 * Service class for managing user account requests.
 * This service provides methods for handling user account requests, including
 * retrieving active and completed requests, deleting user requests, finding requests by ID,
 * saving new requests, and marking requests as completed.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.service;

import com.example.proiectfinal.model.UserRequest;
import com.example.proiectfinal.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserRequestService {
    private final UserRequestRepository userRequestRepository;

    @Autowired
    public UserRequestService(UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    /**
     * Retrieves a list of all active user account requests.
     *
     * @return List of active user account requests.
     */
    public List<UserRequest> findAllActive() {
        List<UserRequest> allActive = new ArrayList<>();
        for (UserRequest userRequest : userRequestRepository.findAll()) {
            if (!userRequest.isCompleted()) {
                allActive.add(userRequest);
            }
        }
        return allActive;
    }

    /**
     * Retrieves a list of all completed user account requests.
     *
     * @return List of completed user account requests.
     */
    public List<UserRequest> findAllCompleted() {
        List<UserRequest> allCompleted = new ArrayList<>();
        for (UserRequest userRequest : userRequestRepository.findAll()) {
            if (userRequest.isCompleted()) {
                allCompleted.add(userRequest);
            }
        }
        return allCompleted;
    }

    /**
     * Deletes a user account request by its unique ID, if it is not completed.
     *
     * @param id The ID of the user account request to delete.
     */
    public void deleteUserRequest(int id) {
        for (UserRequest userReq : userRequestRepository.findAll()) {
            if (userReq.getId() == id && !userReq.isCompleted()) {
                userRequestRepository.deleteById(id);
            }
        }
    }

    /**
     * Retrieves a user account request by its unique ID.
     *
     * @param id The ID of the user account request to retrieve.
     * @return The user account request with the specified ID.
     * @throws RuntimeException if no requests are found with the given ID.
     */
    public UserRequest findById(int id) {
        return userRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("No requests found with id: " + id));
    }

    /**
     * Saves a new user account request.
     *
     * @param userRequest The user account request to save.
     */
    public void save(UserRequest userRequest) {
        userRequestRepository.save(userRequest);
    }

    /**
     * Marks a user account request as completed.
     *
     * @param userRequest The user account request to mark as completed.
     * @return List of completed user account requests (including the marked request).
     */
    public List<UserRequest> setRequestAsCompleted(UserRequest userRequest) {
        List<UserRequest> completed = new ArrayList<>();
        if (!userRequest.isCompleted()) {
            userRequest.setCompleted(true);
            completed.add(userRequest);
            userRequestRepository.save(userRequest);
        }
        return completed;
    }


}
