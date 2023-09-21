package com.example.proiectfinal.utils;

import com.example.proiectfinal.exception.CustomValidationException;
import com.example.proiectfinal.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {
    private UserValidation userValidation;

    @BeforeEach
    void setUp() {
        userValidation = new UserValidation();
    }

    @Test
    void validateNewUser() {
        User user = User.builder()
                .role("ADMIN")
                .cnp("1234567890098")
                .first_name("Andrei")
                .last_name("Marin")
                .email("name@email.com")
                .image_link("https://test-image.test.link-test")
                .gender("Male")
                .department("IT")
                .job_title("Manager")
                .hiring_date("23.02.2009")
                .contract("Full-Time")
                .salary(123)
                .build();
        assertDoesNotThrow(() -> userValidation.validateNewUser(user));
        System.out.println("Test - NEW USER = success");
    }

    @Test
    void validateGender() {
        User user = User.builder()
                .gender("Male")
                .build();

        assertDoesNotThrow(() -> userValidation.validateGender(user));
        System.out.println("Test - Gender = success");
    }

    @Test
    void validateGender_throws_custom_exception_successfully() {
        User user = User.builder()
                .gender("Test")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateGender(user);
        });

        System.out.println("Test - Gender = throws Custom Exception -> success");
    }


    @Test
    void validateCNP() {
        User user = User.builder()
                .cnp("1234567890123")
                .build();

        assertDoesNotThrow(() -> userValidation.validateCNP(user));
        userValidation.validateCNP(user);
        System.out.println("Test - CNP = success");
    }

    @Test
    void validateCNP_throws_custom_exception_successfully() {
        User user = User.builder()
                .cnp("12a4r6678t")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateCNP(user);
        });

        System.out.println("Test - CNP = throws Custom Exception -> success");
    }

    @Test
    void validateName() {
        User user = User.builder()
                .first_name("Ana")
                .last_name("Maria")
                .build();

        assertDoesNotThrow(() -> userValidation.validateName(user));
        System.out.println("Test - Name = success");
    }

    @Test
    void validateName_throws_custom_exception_successfully() {
        User user = User.builder()
                .build();

        assertThrows(NullPointerException.class, () -> {
            userValidation.validateName(user);
        });

        System.out.println("Test - Name = throws Custom Exception -> success");
    }

    @Test
    void validateEmail() {
        User user = User.builder()
                .email("ana@email.test.com")
                .build();

        assertDoesNotThrow(() -> userValidation.validateEmail(user));
        System.out.println("Test - Email = success");
    }

    @Test
    void validateEmail_throws_custom_exception_successfully() {
        User user = User.builder()
                .email("ana.email.com")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateEmail(user);
        });

        System.out.println("Test - Email = throws Custom Exception -> success");
    }

    @Test
    void validateImageLink() {
        User user = User.builder()
                .image_link("https://test-imagine.test")
                .build();

        assertDoesNotThrow(() -> userValidation.validateImageLink(user));
        System.out.println("Test - Image Link - starts with \"https://\" = success");
    }

    @Test
    void validateImage_Link_throws_custom_exception_successfully() {
        User user = User.builder()
                .image_link("http://")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateImageLink(user);
        });

        System.out.println("Test - Image Link = throws Custom Exception -> success");
    }

    @Test
    void validateRole() {
        User user = User.builder()
                .role("ADMIN")
                .build();

        assertDoesNotThrow(() -> userValidation.validateRole(user));
        System.out.println("Test - Role = success");
    }

    @Test
    void validateRole_throws_custom_exception_successfully() {
        User user = User.builder()
                .role("ABC")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateRole(user);
        });

        System.out.println("Test - Role = throws Custom Exception -> success");
    }

    @Test
    void validateJobTitle() {
        User user = User.builder()
                .job_title("Manager")
                .build();

        assertDoesNotThrow(() -> userValidation.validateJobTitle(user));
        System.out.println("Test - JobTitle = success");
    }

    @Test
    void validate_Job_Title_throws_custom_exception_successfully() {
        User user = User.builder()
                .job_title("Job Title")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateJobTitle(user);
        });

        System.out.println("Test - Job Title = throws Custom Exception -> success");
    }

    @Test
    void validateDepartment() {
        User user = User.builder()
                .department("IT")
                .build();

        assertDoesNotThrow(() -> userValidation.validateDepartment(user));
        System.out.println("Test - Department = success");
    }

    @Test
    void validate_Department_throws_custom_exception_successfully() {
        User user = User.builder()
                .department("Departament")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateDepartment(user);
        });

        System.out.println("Test - Department = throws Custom Exception -> success");
    }

    @Test
    void validateHiringDate() {
        User user = User.builder()
                .hiring_date("23.01.2002")
                .build();

        assertDoesNotThrow(() -> userValidation.validateHiringDate(user));
        System.out.println("Test - HiringDate = success");
    }

    @Test
    void validate_Hiring_Date_throws_custom_exception_successfully() {
        User user = User.builder()
                .hiring_date("1234598777")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateHiringDate(user);
        });

        System.out.println("Test - Hiring Date = throws Custom Exception -> success");
    }

    @Test
    void validateContract() {
        User user = User.builder()
                .contract("Full-Time")
                .build();

        assertDoesNotThrow(() -> userValidation.validateContract(user));
        System.out.println("Test - Contract = success");
    }

    @Test
    void validate_Contract_throws_custom_exception_successfully() {
        User user = User.builder()
                .contract("Contract")
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateContract(user);
        });

        System.out.println("Test - Contract = throws Custom Exception -> success");
    }

    @Test
    void validateSalary() {
        User user = User.builder()
                .salary(19878)
                .build();

        assertDoesNotThrow(() -> userValidation.validateSalary(user));
        System.out.println("Test - Salary = success");
    }

    @Test
    void validate_Salary_throws_custom_exception_successfully() {
        User user = User.builder()
                .salary(0)
                .build();

        assertThrows(CustomValidationException.class, () -> {
            userValidation.validateSalary(user);
        });

        System.out.println("Test - Salary= throws Custom Exception -> success");
    }
}