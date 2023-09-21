/**
 * Utility class for validating user data.
 * This class contains methods to validate various user attributes such as gender, CNP, name,
 * email, image link, role, job title, department, hiring date, contract, and salary.
 * It ensures that user data adheres to specific syntax and format requirements.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.utils;


import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserValidation {
    public void validateNewUser(User user) {
        validateGender(user);
        validateCNP(user);
        validateName(user);
        validateEmail(user);
        validateImageLink(user);
        validateRole(user);
        validateJobTitle(user);
        validateDepartment(user);
        validateHiringDate(user);
        validateContract(user);
        validateSalary(user);

    }

    void validateGender(User user) {
        if (!user.getGender().equals("Male") && !user.getGender().equals("Female")) {
            throw new CustomValidationException("Role syntax error: must be: Male | Female");
        }
        System.out.println("Validate gender: success");
    }

    void validateCNP(User user) {
        String cnp = user.getCnp();
        if (cnp.length() != 13 || !cnp.matches("\\d{13}")) {
            throw new CustomValidationException("CNP syntax error: must be 13 digits and contain only numbers");
        }
        System.out.println("Validate CNP: success");
    }


    void validateName(User user) {
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();

        if (!firstName.matches("^[A-Za-z]+$") || !lastName.matches("^[A-Za-z]+$")) {
            throw new CustomValidationException("Name syntax error: must contain only letters (e.g., John Smith)");
        }
        System.out.println("Validate Name: success");
    }


    void validateEmail(User user) {
        String email = user.getEmail();
        String regex = "^(.+)@(.+)$";

        if (!email.matches(regex)) {
            throw new CustomValidationException("Email syntax error: must be in the format name@email.com");
        }
        System.out.println("Validate Email: success");
    }

    void validateImageLink(User user) {
        String imageLink = user.getImage_link();

        if (!imageLink.startsWith("https://")) {
            throw new CustomValidationException("Image link must start with 'https://'.");
        }

        System.out.println("Validate Image Link: success");
    }


    void validateRole(User user) {
        String role = user.getRole();
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new CustomValidationException("Role syntax error: must be ADMIN or USER");
        }
        System.out.println("Validate Role: success");
    }


    void validateJobTitle(User user) {
        String jobTitle = user.getJob_title();
        List<String> allowedJobTitles = Arrays.asList("CEO", "CTO", "CFO", "Manager", "IT Support Engineer", "Developer", "QA", "Product Owner", "HR", "HR Manager", "Account Payable Operation", "Account Payable Manager", "Marketing Assistant", "Marketing Manager");

        if (!allowedJobTitles.contains(jobTitle)) {
            throw new CustomValidationException("Invalid job title: " + jobTitle);
        }
        System.out.println("Validate Job Title: success");
    }


    void validateDepartment(User user) {
        String department = user.getDepartment();
        List<String> allowedDepartments = Arrays.asList("Management", "IT", "Development", "HR", "Finance", "Marketing");

        if (!allowedDepartments.contains(department)) {
            throw new CustomValidationException("Invalid department name: " + department);
        }
        System.out.println("Validate dep: success");
    }


    void validateHiringDate(User user) {
        String hiringDate = user.getHiring_date();
        String regex = "^(\\d{2}[-.]\\d{2}[-.]\\d{4})$"; // Matches dd-mm-yyyy or dd.mm.yyyy

        if (!hiringDate.matches(regex)) {
            throw new CustomValidationException("Invalid hiring date: must be in format dd-mm-yyyy or dd.mm.yyyy");
        }
        System.out.println("Validate hiring date: success");
    }


    void validateContract(User user) {
        String contractType = user.getContract();
        List<String> allowedContractTypes = Arrays.asList("Full-Time", "Part-Time", "Internship");

        if (!allowedContractTypes.contains(contractType)) {
            throw new CustomValidationException("Invalid contract type: " + contractType);
        }
        System.out.println("Validate contract: success");
    }


    void validateSalary(User user) {
        int salary = user.getSalary();

        if (salary <= 0) {
            throw new CustomValidationException("Invalid salary: must be greater than 0");
        }
        System.out.println("Validate salary: success");
    }

}
