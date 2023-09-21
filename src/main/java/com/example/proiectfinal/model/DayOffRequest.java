package com.example.proiectfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity class representing a day off request.
 * This class stores information about a user's request for a day off or vacation,
 * including the start and end dates, the number of requested days, and whether
 * the request has been canceled.
 *
 * @author Mihaita Hingan
 */
@Entity
@Table(name = "holidays")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfRequestedDays;
    private boolean isCanceled;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
