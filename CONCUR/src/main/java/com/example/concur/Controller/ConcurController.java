package com.example.concur.Controller;

import com.example.concur.Entity.User;
import com.example.concur.Serializer.Decode;
import com.example.concur.Service.ConcurService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@RestController
@RestControllerAdvice
public class ConcurController {

    @Autowired
    private ConcurService service;

    @Autowired
    private Decode decode;

    private Logger logger = LoggerFactory.getLogger("ConcurController");

    //Register a user into the database
    @PostMapping (value="/signup")
    public String signUpUser(@RequestBody User user){
        service.signUpUser(user);
        return "You have signed up successfully!";
    }

    //Get user information out of the database
    @GetMapping(value = "/fetch/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public User getUserByEmail(@PathVariable(value = "email")String email){
        return service.getUserByEmail(email);
    }

    //Update user info in the database
    @PutMapping (value="/user/update/{email}")
    public String updateUserById(@PathVariable(value = "email") String email, @RequestParam(value="name", required = false) Optional<String> name,
                             @RequestParam(value="username", required = false) Optional<String> username, @RequestParam(value="id", required = false) Optional<Integer> id,
                             @RequestParam(value="street", required = false) Optional<String> street, @RequestParam(value="suite", required = false) Optional<String> suite,
                             @RequestParam(value="city", required = false) Optional<String> city, @RequestParam(value="zipcode", required = false) Optional<String> zipcode,
                             @RequestParam(value="phone", required = false) Optional<String> phone, @RequestParam(value="website", required = false) Optional<String> website){
        service.updateUserByEmail(email, name, username, id, street, suite, city, zipcode, phone, website);
        return "You have updated your information successfully!";
    }

    //delete designated user profile by id
    @DeleteMapping(value = "/user/delete/{email}")
    public String deleteUserByEmail(@PathVariable(value = "email")String email){
        service.deleteUserByEmail(email);
        return "The user has been deleted successfully!";
    }

    //fetch user information with token and send back to customer service
    @GetMapping (value="/get/{token}")
    public String getUserInfo(@PathVariable(value = "token")String token){
        Date exp;
        try{
            exp = decode.parseJwt(token).getBody().getExpiration();
        }catch (ExpiredJwtException ex){
            throw ex;
        }finally{
            logger.info(token);
        }
        Instant now = Instant.now();
        Date threshold = Date.from(now.plus(2l, ChronoUnit.MINUTES));
        if(!exp.before(threshold)) {
            String email = decode.parseJwt(token).getBody().get("email", String.class);
            service.getUserInfo(email);
            return "The token is valid! Fetching user information and sending back to Customer System. " +
                    "Check Concur console for any exceptions.";
        }else {
            logger.warn("The token will expire in two minutes!");
            logger.info(token);
            return "The token is invalid. Please refresh it in CWT: " + token;
        }
    }

    //catch block for expired token exception
    @ExceptionHandler(ExpiredJwtException.class)
    public String handleExpiredJwtException(ExpiredJwtException ex){
        logger.info(ex.getMessage());
        return ex.getMessage();
    }

}
