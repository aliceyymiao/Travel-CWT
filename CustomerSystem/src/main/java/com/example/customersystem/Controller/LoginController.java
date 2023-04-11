package com.example.customersystem.Controller;

import com.example.customersystem.Entity.Request;
import com.example.customersystem.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RestControllerAdvice
public class LoginController {

    @Autowired
    LoginService service;

    //Use email to retrieve information from CWT and then Concur
    @GetMapping(value = "/login")
    public String sendMessage(@RequestParam(value = "email") String email,
                              @RequestParam(value = "firstname") String firstName,
                              @RequestParam(value = "lastname") String lastName){
        Request request = new Request();
        request.setEmail(email);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        service.sendNotification(request);
        return "Login request sent successfully. Fetching user data...";
    }
}
