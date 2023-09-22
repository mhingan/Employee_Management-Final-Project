package com.example.proiectfinal.service;

import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class StatisticsServiceTest {
    //TODO: ADD INFO/COMMENTS
    private StatisticsService statisticsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        statisticsService = new StatisticsService(userRepository);
    }

    private User createUser(String gender, String contract) {
        User user = new User();
        user.setRole("USER");
        user.setGender(gender);
        user.setContract(contract);
        return user;
    }

    @Test
    public void testGetAllMaleEmployees() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("Male", "Full-Time"));
        userList.add(createUser("Female","Part-Time"));
        userList.add(createUser("Male","Full-Time"));

        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllMaleEmployees();

        assertEquals(2, result); // Expecting 2 male employees
        System.out.println("Test: Find all male employees: OK");
    }



    @Test
    void getAllFemaleEmployees() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("Male", "Full-Time"));
        userList.add(createUser("Female","Part-Time"));
        userList.add(createUser("Male","Full-Time"));

        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllFemaleEmployees();

        assertEquals(1, result); // Expecting 1 female employee
        System.out.println("Test: Find all female employees: OK");
    }

    @Test
    void getAllFullTime() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("Male", "Full-Time"));
        userList.add(createUser("Female","Part-Time"));
        userList.add(createUser("Male","Full-Time"));

        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllFullTime();

        assertEquals(2, result); // Expecting 2 full-time contract employees
        System.out.println("Test: Find all Full-Time contract employees: OK");
    }

    @Test
    void getAllPartTime() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("Male", "Full-Time"));
        userList.add(createUser("Female","Part-Time"));
        userList.add(createUser("Male","Full-Time"));

        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllPartTime();

        assertEquals(1, result); // Expecting 1 part-time contract employees
        System.out.println("Test: Find all Part-Time contract employees: OK");
    }

    @Test
    void getAllInternships() {
        List<User> userList = new ArrayList<>();
        userList.add(createUser("Male", "Internship"));
        userList.add(createUser("Female","Internship"));
        userList.add(createUser("Male","Full-Time"));

        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllInternships();

        assertEquals(2, result); // Expecting 1 internship contract employees
        System.out.println("Test: Find all Internship contract employees: OK");
    }

    private User createUserForDepartmentTest(String department) {
        User user2 = new User();
        user2.setRole("USER");
        user2.setDepartment(department);
        return user2;
    }

    @Test
    void getAllManagement() {
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("Management"));
        userList.add(createUserForDepartmentTest("IT"));
        userList.add(createUserForDepartmentTest("HR"));


        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllManagement();

        assertEquals(1, result); // Expecting 1 MANAGEMENT employee
        System.out.println("Test: Find all employees from Management department: OK");
    }

    @Test
    void getAllIT() {
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("Management"));
        userList.add(createUserForDepartmentTest("IT"));
        userList.add(createUserForDepartmentTest("HR"));


        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllIT();

        assertEquals(1, result); // Expecting 1 IT employee
        System.out.println("Test: Find all employees from IT department: OK");
    }

    @Test
    void getAllDevelopment() {
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("Development"));
        userList.add(createUserForDepartmentTest("IT"));
        userList.add(createUserForDepartmentTest("Development"));


        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllDevelopment();

        assertEquals(2, result); // Expecting 2 DEVELOPMENT employees
        System.out.println("Test: Find all employees from Development department: OK");
    }

    @Test
    void getAllFinance() {
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("Finance"));
        userList.add(createUserForDepartmentTest("IT"));
        userList.add(createUserForDepartmentTest("Development"));


        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllFinance();

        assertEquals(1, result); // Expecting 1 FINANCE employees
        System.out.println("Test: Find all employees from Finance department: OK");
    }

    @Test
    void getAllMarketing() {
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("Marketing"));
        userList.add(createUserForDepartmentTest("IT"));
        userList.add(createUserForDepartmentTest("Development"));


        when(userRepository.findAll()).thenReturn(userList);

        int result = statisticsService.getAllMarketing();

        assertEquals(1, result); // Expecting 1 MARKETING employees
        System.out.println("Test: Find all employees from Marketing department: OK");
    }

    @Test
    void getAllHR() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(createUserForDepartmentTest("HR"));
        userList.add(createUserForDepartmentTest("HR"));
        userList.add(createUserForDepartmentTest("Development"));


        when(userRepository.findAll()).thenReturn(userList);


        int result = statisticsService.getAllHR();

        assertEquals(2, result);
        System.out.println("Test: Find all employees from HR department: OK");
    }
}