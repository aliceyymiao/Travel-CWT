package com.example.concur.Service;

import com.example.concur.Entity.Address;
import com.example.concur.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.concur.Repository.JPAUserRepo;

import java.util.Optional;

@Service
public class ConcurService {

    @Autowired
    private JPAUserRepo jpaUserRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger("ConcurService");

    public String signUpUser(User user){
        jpaUserRepo.save(user);
        return "You have signed up successfully";
    }

    public User getUserByEmail(String email){
        Optional<User> loadUser = jpaUserRepo.findById(email);
        if(loadUser.isPresent()){
            return loadUser.get();
        }
        return null;
    }

    public void updateUserByEmail(String email, Optional<String> name,
                               Optional<String> username, Optional<Integer> id,
                               Optional<String> street, Optional<String> suite,
                               Optional<String> city, Optional<String> zipcode,
                               Optional<String> phone, Optional<String> website){
        Optional<User> loadUser = jpaUserRepo.findById(email);
        if(!loadUser.isPresent()){
            throw new RuntimeException("User not found!");
        }
        User user = loadUser.get();
        Address address = user.getAddress();
        user.setName(name.orElse(user.getName()));
        user.setUsername(username.orElse(user.getUsername()));
        user.setId(id.orElse(user.getId()));
        address.setStreet(street.orElse(address.getStreet()));
        address.setSuite(suite.orElse(address.getSuite()));
        address.setCity(city.orElse(address.getCity()));
        address.setZipcode(zipcode.orElse(address.getZipcode()));
        user.setAddress(address);
        user.setPhone(phone.orElse(user.getPhone()));
        user.setWebsite(website.orElse(user.getWebsite()));
        jpaUserRepo.save(user);
    }

    public void deleteUserByEmail(String email){
        Optional<User> loadUser = jpaUserRepo.findById(email);
        if(loadUser.isPresent()){
            User user = loadUser.get();
            jpaUserRepo.delete(user);
        }
    }

    public void getUserInfo(String email){
        Optional<User> loadUser = jpaUserRepo.findById(email);
        if(loadUser.isPresent()){
            User user = loadUser.get();
            kafkaTemplate.send("userInfo", user.toString());
        }else {
            logger.error("The user does not exist!");
        }
    }

}
