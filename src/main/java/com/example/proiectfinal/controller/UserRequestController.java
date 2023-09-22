/**
 * Controller class for handling user account request operations.
 * This controller manages user requests for new accounts and provides
 * functionality to submit account requests via a signup form.
 *
 * @author Mihaita Hingan
 */
package com.example.proiectfinal.controller;

import com.example.proiectfinal.model.UserRequest;
import com.example.proiectfinal.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserRequestController {

    UserRequestService userRequestService;

    @Autowired
    public UserRequestController(UserRequestService userRequestService) {
        this.userRequestService = userRequestService;
    }


    @GetMapping("/signup")
    public String getRequestAccountPage() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String addNewProduct(@RequestParam("name") String name,
                                @RequestParam("email") String email) {
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .name(name)
                .build();

        userRequestService.save(userRequest);


        return "/user/request-success";
    }



}
