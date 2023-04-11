package com.example.cwt.Controller;

import com.example.cwt.Service.CwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
public class CwtController {

    @Autowired
    private CwtService service;

    //Refresh the token when it's expired or will expire in 2 minutes
    @PutMapping(value="/refresh/{token}")
    public String refreshToken(@PathVariable(value = "token") String token){
        service.refreshToken(token);
        return "The token has been refreshed successfully!";
    }
}
