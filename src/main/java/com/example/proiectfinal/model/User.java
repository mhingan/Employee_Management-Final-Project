/**
 * Entity class representing a user in the system.
 * This class stores information about a user, including identification details,
 * personal information, educational background, job-related details, and more.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

@Builder
@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //identification
    private int id;
    private String role;
    private String cnp;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String hashedPassword;

    //personal
    private String image_link;
    private String gender;
    private String address;
    private String phone_number;

    //todo - as object
    //todo - remove hashedpassword
    //study
    private String degree1;
    private String degree2;
    private String degree3;
    private String degree4;

    //job
    private String department;
    private String job_title;
    private String hiring_date;
    private String contract;
    private int salary;
    private int holiday_day;

    //devices provided by employee
    private List<String> devices;

    public void setPassword(String password) {
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
