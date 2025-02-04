/**
 * AdminController - Controller class for handling admin-related actions and views.
 * <p>
 * This class defines various methods for managing employees, user requests, and statistics in the admin panel.
 *
 * @author Mihaita Hingan
 */

package com.example.proiectfinal.controller;

import com.example.proiectfinal.model.User;
import com.example.proiectfinal.model.UserRequest;
import com.example.proiectfinal.service.StatisticsService;
import com.example.proiectfinal.service.UserRequestService;
import com.example.proiectfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRequestService userRequestService;
    private final StatisticsService service;

    @Autowired
    public AdminController(UserService userService, UserRequestService userRequestService, StatisticsService service) {
        this.userService = userService;
        this.userRequestService = userRequestService;
        this.service = service;
    }

    /**
     * Displays the admin panel.
     *
     * @return The admin panel view.
     */
    @GetMapping("/panel")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getHome() {
        return "admin/admin-panel";
    }

    //todo - add new employee

    /**
     * Displays the page for adding a new employee.
     *
     * @return The add-employee view.
     */
    @GetMapping("/employees/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAddProductPage() {
        return "admin/add-employee";
    }

    /**
     * Handles the submission of a new employee.
     *
     * @param role        The role of the employee.
     * @param cnp         The CNP (personal identification number) of the employee.
     * @param first_name  The first name of the employee.
     * @param last_name   The last name of the employee.
     * @param email       The email of the employee.
     * @param password    The password of the employee (hashed using BCrypt).
     * @param gender      The gender of the employee.
     * @param image_link  The link to the employee's image.
     * @param address     The address of the employee.
     * @param phone       The phone number of the employee.
     * @param degree1     The first degree of the employee.
     * @param degree2     The second degree of the employee.
     * @param degree3     The third degree of the employee.
     * @param degree4     The fourth degree of the employee.
     * @param department  The department of the employee.
     * @param title       The job title of the employee.
     * @param hiring_date The hiring date of the employee.
     * @param contract    The type of contract of the employee.
     * @param salary      The salary of the employee.
     * @param holidays    The number of holiday days for the employee.
     * @param devices     The list of devices assigned to the employee.
     * @return The admin panel view.
     */
    @PostMapping("/employees/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addNewProduct(@RequestParam("role") String role,
                                @RequestParam("cnp") String cnp,
                                @RequestParam("first_name") String first_name,
                                @RequestParam("last_name") String last_name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("gender") String gender,
                                @RequestParam("image_link") String image_link,
                                @RequestParam("address") String address,
                                @RequestParam("phone_number") String phone,
                                @RequestParam("degree1") String degree1,
                                @RequestParam("degree2") String degree2,
                                @RequestParam("degree3") String degree3,
                                @RequestParam("degree4") String degree4,
                                @RequestParam("department") String department,
                                @RequestParam("job_title") String title,
                                @RequestParam("hiring_date") String hiring_date,
                                @RequestParam("contract") String contract,
                                @RequestParam("salary") int salary,
                                @RequestParam("holiday_day") int holidays,
                                @RequestParam("devices") List<String> devices) {
        User user = User.builder()
                .role(role)
                .cnp(cnp)
                .first_name(first_name)
                .last_name(last_name)
                .email(email)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .image_link(image_link)
                .gender(gender)
                .address(address)
                .phone_number(phone)
                .degree1(degree1)
                .degree2(degree2)
                .degree3(degree3)
                .degree4(degree4)
                .department(department)
                .job_title(title)
                .hiring_date(hiring_date)
                .contract(contract)
                .salary(salary)
                .holiday_day(holidays)
                .devices(devices)
                .build();

        userService.createUser(user);
        return "redirect:/admin/panel";
    }


    @GetMapping("/employees")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAllUsers(Model model) {
        List<User> employees = userService.findAll();

        model.addAttribute("employees", employees);

        return "admin/all-employees";
    }

    //todo - edit an employee
    @GetMapping("/employees/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getProductToEdit(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "admin/edit-user";
    }

    @RequestMapping(value = "/employees/edit/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute User user) {
        User existingUser = userService.findById(id);

        existingUser.setRole(user.getRole());
        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone_number(user.getPhone_number());
        existingUser.setDegree1(user.getDegree1());
        existingUser.setDegree2(user.getDegree2());
        existingUser.setDegree3(user.getDegree3());
        existingUser.setDegree4(user.getDegree4());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setJob_title(user.getJob_title());
        existingUser.setContract(user.getContract());
        existingUser.setHoliday_day(user.getHoliday_day());
        existingUser.setDevices(user.getDevices());

        userService.update(existingUser);

        return "redirect:http://localhost:8080/admin/employees";
    }

    //todo
    @GetMapping("/employees/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteProduct(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "admin/error/delete-user-confirmation";
    }

    @RequestMapping(value = "/employees/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteEmployee(@PathVariable("id") int id) {
        User user = userService.findById(id);

        userService.deleteUser(user.getId());

        return "redirect:/admin/panel";
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getStatistics(Model model) {
        List<User> users = userService.findAllActiveUsers();

        int totalActiveUsers = users.size();
        int mUsers = service.getAllMaleEmployees();
        int fUsers = service.getAllFemaleEmployees();
        int fullTime = service.getAllFullTime();
        int partTime = service.getAllPartTime();
        int intern = service.getAllInternships();
        int management = service.getAllManagement();
        int it = service.getAllIT();
        int development = service.getAllDevelopment();
        int hr = service.getAllHR();
        int finance = service.getAllFinance();
        int marketing = service.getAllMarketing();

        LocalDate date = LocalDate.now();

        model.addAttribute("users", users);
        model.addAttribute("totalActiveUsers", totalActiveUsers);
        model.addAttribute("mUsers", mUsers);
        model.addAttribute("fUsers", fUsers);
        model.addAttribute("date", date);
        model.addAttribute("fullTime", fullTime);
        model.addAttribute("partTime", partTime);
        model.addAttribute("intern", intern);
        model.addAttribute("management", management);
        model.addAttribute("it", it);
        model.addAttribute("development", development);
        model.addAttribute("hr", hr);
        model.addAttribute("finance", finance);
        model.addAttribute("marketing", marketing);

        return "admin/statistics";
    }

    @GetMapping("/accounts/requests")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAllRequests(Model model) {

        List<UserRequest> userRequests = userRequestService.findAllActive();

        model.addAttribute("userRequests", userRequests);

        return "admin/accounts-requests";
    }

    @GetMapping("/accounts/requests/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getDeleteRequest(@PathVariable("id") int id, Model model) {

        UserRequest userRequest = userRequestService.findById(id);

        model.addAttribute("userRequest", userRequest);

        return "admin/single-account-request";
    }


    //todo - add info to the rest
    @RequestMapping(value = "/accounts/requests/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteRequest(@PathVariable("id") int id) {
        userRequestService.deleteUserRequest(id);

        return "redirect:/admin/accounts/requests";
    }

    @RequestMapping(value = "/accounts/requests/completed/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String completeRequest(@PathVariable("id") int id) {

        UserRequest userRequest = userRequestService.findById(id);

        userRequestService.setRequestAsCompleted(userRequest);

        return "redirect:/admin/accounts/requests";
    }


    @GetMapping("/accounts/requests/history")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getAllRequestsCompleted(Model model) {

        List<UserRequest> userRequests = userRequestService.findAllCompleted();

        model.addAttribute("userRequests", userRequests);

        return "admin/accounts-requests-completed";
    }

    @GetMapping("/search-by-last_name")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String searchByLastName(@RequestParam("last_name") String last_name, Model model) {
        List<User> employees = userService.findByLastName(last_name);

        model.addAttribute("employees", employees);

        return "admin/all-employees";
    }


}
