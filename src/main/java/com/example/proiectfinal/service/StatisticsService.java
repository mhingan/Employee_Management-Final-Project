/**
 * Service class for generating statistical data related to employees.
 * This service provides methods for retrieving statistics such as the number of male and female employees,
 * full-time and part-time employees, and employees in various departments.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.service;

import com.example.proiectfinal.model.User;
import com.example.proiectfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    private final UserRepository userRepository;

    @Autowired
    public StatisticsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Retrieves the number of male employees.
     *
     * @return The number of male employees.
     */
    public int getAllMaleEmployees() {
        List<User> all = userRepository.findAll();
        List<User> maleList = new ArrayList<>();
        int number = 0;
        for (User user : all) {
            if (user.getRole() != null && user.getRole().equals("USER") && "Male".equals(user.getGender())) {
                maleList.add(user);
                number = maleList.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of female employees.
     *
     * @return The number of female employees.
     */
    public int getAllFemaleEmployees() {
        List<User> all = userRepository.findAll();
        List<User> femaleList = new ArrayList<>();
        int number = 0;
        for (User user : all) {
            if (user.getGender().equals("Female")&&user.getRole().equals("USER")) {
                femaleList.add(user);
                number = femaleList.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of full-time employees.
     *
     * @return The number of full-time employees.
     */
    public int getAllFullTime(){
        List<User> all = userRepository.findAll();
        List<User> fullTimeUsers = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getContract().equals("Full-Time")&&user.getRole().equals("USER")){
                fullTimeUsers.add(user);
                number = fullTimeUsers.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of part-time employees.
     *
     * @return The number of part-time employees.
     */
    public int getAllPartTime(){
        List<User> all = userRepository.findAll();
        List<User> partTimeUsers = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getContract().equals("Part-Time")&&user.getRole().equals("USER")){
                partTimeUsers.add(user);
                number = partTimeUsers.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees on internships.
     *
     * @return The number of employees on internships.
     */
    public int getAllInternships(){
        List<User> all = userRepository.findAll();
        List<User> interns = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getContract().equals("Internship")&&user.getRole().equals("USER")){
                interns.add(user);
                number = interns.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in Management Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllManagement(){
        List<User> all = userRepository.findAll();
        List<User> management = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("Management")&&user.getRole().equals("USER")){
                management.add(user);
                number = management.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in IT Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllIT(){
        List<User> all = userRepository.findAll();
        List<User> it = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("IT")&&user.getRole().equals("USER")){
                it.add(user);
                number = it.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in Development Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllDevelopment(){
        List<User> all = userRepository.findAll();
        List<User> development = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("Development")&&user.getRole().equals("USER")){
                development.add(user);
                number = development.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in Finance Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllFinance(){
        List<User> all = userRepository.findAll();
        List<User> finance = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("Finance")&&user.getRole().equals("USER")){
                finance.add(user);
                number = finance.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in Marketing Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllMarketing(){
        List<User> all = userRepository.findAll();
        List<User> marketing = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("Marketing")&&user.getRole().equals("USER")){
                marketing.add(user);
                number = marketing.size();
            }
        }
        return number;
    }

    /**
     * Retrieves the number of employees in HR Dep.
     *
     * @return The number of employees on specified department.
     */
    public int getAllHR(){
        List<User> all = userRepository.findAll();
        List<User> hr = new ArrayList<>();
        int number = 0;
        for(User user: all){
            if(user.getDepartment().equals("HR")&&user.getRole().equals("USER")){
                hr.add(user);
                number = hr.size();
            }
        }
        return number;
    }
}
