/**
 * A Data Transfer Object (DTO) for representing user-related data.
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String user_id;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private String phone_number;
    private String department;
    private String job_title;
    private String hiring_date;

}

