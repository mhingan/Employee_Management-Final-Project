package com.example.proiectfinal.service;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.DayOffRequest;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.DayOffRequestRepository;
import com.example.proiectfinal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
    /**
     * Service class for managing day off requests.
     * This service provides methods for handling day off requests, including listing all requests,
     * retrieving requests by ID, adding new requests, and canceling requests.
     *
     * @author Mihaita Hingan
     */
    @Service
    public class DayOffRequestService {
        private final DayOffRequestRepository dayOffRequestRepository;
        private final UserRepository userRepository;

        @Autowired
        public DayOffRequestService(DayOffRequestRepository dayOffRequestRepository, UserRepository userRepository) {
            this.dayOffRequestRepository = dayOffRequestRepository;
            this.userRepository = userRepository;
        }

        /**
         * Retrieves a list of all off requests.
         *
         * @return List of day off requests.
         */
        public List<DayOffRequest> allDaysOff() {
            return dayOffRequestRepository.findAll();
        }

        /**
         * Retrieves a day off request by its unique ID.
         *
         * @param id The ID of the day off request to retrieve.
         * @return The day off request with the specified ID.
         * @throws CustomValidationException if the request is not found with the given ID.
         */
        public DayOffRequest getById(int id) {
            return dayOffRequestRepository.findById(id).orElseThrow(() -> new CustomValidationException("not found with id " + id));
        }

        /**
         * Adds a new day off request for a user.
         *
         * @param dayOffRequest The day off request to add.
         * @param user          The user associated with the request.
         */
        public void addNewDaysOff(DayOffRequest dayOffRequest, User user) {
            dayOffRequest.setUser(user);
            dayOffRequestRepository.save(dayOffRequest);
        }

        /**
         * Cancels a day off request and updates the user's available holiday days.
         *
         * @param dayOffRequest The day off request to cancel.
         * @param user          The user associated with the request.
         * @throws CustomValidationException if the request is already canceled or the holiday has already started.
         */
        public void cancelRequest(DayOffRequest dayOffRequest, User user) {
            if (dayOffRequest.isCanceled()) {
                throw new CustomValidationException("Request already canceled");
            } else if (dayOffRequest.getStartDate().isBefore(LocalDate.now())) {
                throw new CustomValidationException("Cannot cancel, holiday already started.");
            } else {
                dayOffRequest.setCanceled(true);
                int canceledDays = dayOffRequest.getNumberOfRequestedDays();
                int oldDays = user.getHoliday_day() + canceledDays;
                user.setHoliday_day(oldDays);
                userRepository.save(user);
                dayOffRequestRepository.save(dayOffRequest);
            }
        }
    }




