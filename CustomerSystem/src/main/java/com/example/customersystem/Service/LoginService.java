package com.example.customersystem.Service;

import com.example.customersystem.Entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.*;

@Service
public class LoginService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger("CustomerSystemService");

    public void sendNotification(Request request) {
        sendInfo(request);
    }

    private void sendInfo(Request request){
        String email = request.getEmail();
        kafkaTemplate.send("topicEmail", email);
    }

    @KafkaListener(topics = "userInfo")
    public void logMessageEmail(String message){
        logger.info(message);
    }

}
