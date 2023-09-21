/**
 * CustomErrorController - Controller class for handling error-related actions and views.
 * <p>
 * This class defines methods that are managing the user view and errors.
 *
 * @author Mihaita Hingan
 */

package com.example.proiectfinal.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        HttpStatus status = getStatus(webRequest);
        int statusCode = status.value();


        model.addAttribute("errorCode", statusCode);
        model.addAttribute("errorMessage", getErrorMessage(statusCode));


        return "admin/error/generalError";
    }


    private String getErrorMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad Request";
            case 404 -> "Page Not Found";
            case 403 -> "Forbidden";
            case 500 -> "Internal Server Error";
            default -> "An error occurred";
        };
    }


    private HttpStatus getStatus(WebRequest webRequest) {
        Integer statusCode = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", WebRequest.SCOPE_REQUEST);
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                // Handle invalid status code
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
