/**
 * Controller class for managing user-related operations and pages.
 * This controller handles various user actions such as viewing user profiles,
 * updating user information, requesting days off, and more.
 * It also includes functionality for administrators to manage employees.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.controller;

import com.example.proiectfinal.model.DayOffRequest;
import com.example.proiectfinal.model.User;
import com.example.proiectfinal.service.DayOffRequestService;
import com.example.proiectfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final DayOffRequestService dayOffRequestService;

    @Autowired
    public UserController(UserService userService, DayOffRequestService dayOffRequestService) {
        this.userService = userService;
        this.dayOffRequestService = dayOffRequestService;
    }

    @GetMapping("/company")
    public String getCompanyInfoPage() {
        return "company";
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/employees")
    public String getAllUsers(Model model) {
        List<User> employees = userService.findAll();
        model.addAttribute("employees", employees);
        return "all-employees";
    }


    @GetMapping("/employees/{id}")
    public String getSingleEmployee(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("employee", user);
        return "user/single-employee";
    }


    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        return "user/dashboard";
    }


    @GetMapping("/my_profile")
    public String getProfile(Model model) {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        return "user/profile/profile";
    }


    @GetMapping("/my_profile/update_info")
    public String getProfileToEdit(Model model) {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        return "user/profile/update-information";
    }


    @RequestMapping(value = "/my_profile/update_info", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@ModelAttribute User user) {


        User existingUser = userService.findCurrentUser();
        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setImage_link(user.getImage_link());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone_number(user.getPhone_number());
        existingUser.setDegree1(user.getDegree1());
        existingUser.setDegree2(user.getDegree2());
        existingUser.setDegree3(user.getDegree3());
        existingUser.setDegree4(user.getDegree4());

        userService.update(existingUser);

        return "user/profile/update-information";
    }

    @GetMapping("/my_profile/request_days_off")
    public String getDayOffPage(Model model) {
        User user = userService.findCurrentUser();
        List<DayOffRequest> daysOff = dayOffRequestService.allDaysOff();
        int currentNumberOfDays = user.getHoliday_day();
        model.addAttribute("currentNumberOfDays", currentNumberOfDays);
        model.addAttribute("daysOff", daysOff);
        return "user/profile/days-off";
    }


    @RequestMapping(value = "/my_profile/request_days_off", method = RequestMethod.POST)
    public String getDayOff(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, Model model) {

        int numberOfDays = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);
        DayOffRequest dayOffRequest = DayOffRequest.builder().startDate(startDate).endDate(endDate).numberOfRequestedDays(numberOfDays).isCanceled(false).build();
        dayOffRequestService.addNewDaysOff(dayOffRequest, userService.findCurrentUser());

        User user = userService.findCurrentUser();
        userService.requestHoliday(user, startDate, endDate);
        model.addAttribute("user", user);
        return "user/profile/profile";
    }



    @GetMapping("/my_profile/request_days_off/{id}")
    public String getDeleteRequest(@PathVariable("id") int id, Model model) {
        DayOffRequest dayOffRequest = dayOffRequestService.getById(id);
        model.addAttribute("dayOffRequest", dayOffRequest);
        return "user/profile/cancel-holiday";
    }

    @RequestMapping(value = "/my_profile/request_days_off/{id}", method = RequestMethod.POST)
    public String deleteRequest(@PathVariable("id") int id, Model model) {
        User user = userService.findCurrentUser();
        DayOffRequest dayOffRequest = dayOffRequestService.getById(id);
        dayOffRequestService.cancelRequest(dayOffRequest, user);
        model.addAttribute("dayOffRequest", dayOffRequest);
        return "redirect:/dashboard";
    }

    @GetMapping("/search-by-last_name")
    public String searchByLastName(@RequestParam("last_name") String last_name, Model model) {
        List<User> employees = userService.findByLastName(last_name);
        model.addAttribute("employees", employees);
        return "all-employees";
    }


    @GetMapping("/my_profile/change_password/{userId}")
    public String getChg(@PathVariable int userId, Model model){
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "user/profile/change-password";

    }

    @RequestMapping(value = "/my_profile/change_password/{userId}", method = RequestMethod.POST)
    public String changePassword(@PathVariable int userId, @RequestParam String password) {
        userService.changePassword(userId, password);
        return "redirect:/my_profile";
    }


}

