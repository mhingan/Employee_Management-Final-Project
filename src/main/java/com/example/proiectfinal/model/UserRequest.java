/**
 * Entity class representing a user account request.
 * This class stores information about a user's request for a new account,
 * including the employee name, email address, and whether the request has been completed.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "accounts_requests")
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    int id;
    private String name;
    private String email;
    private boolean isCompleted;
}
