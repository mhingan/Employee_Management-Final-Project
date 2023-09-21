/**@author Mihaita Hingan*/
package com.example.proiectfinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class GeneralController {
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
