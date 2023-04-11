package com.example.cwt.Service;

import com.example.cwt.Entity.RefreshToken;
import com.example.cwt.Entity.TokenInfo;
import com.example.cwt.Repository.JPARefreshTokenRepo;
import com.example.cwt.Repository.JPATokenRepo;
import com.example.cwt.Serializer.Decode;
import com.example.cwt.Serializer.Encode;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CwtService {

    @Autowired
    private JPATokenRepo jpaTokenRepo;

    @Autowired
    private JPARefreshTokenRepo jpaRefreshTokenRepo;

    @Autowired
    private Encode encode;

    @Autowired
    private Decode decode;

    private Logger logger = LoggerFactory.getLogger("CwtService");

    //consumes the email sent from Customer System, encode it and store
    //it into the database
    @KafkaListener(topics = "topicEmail")
    public void getMessageEmail(String email) throws ExpiredJwtException{
        Optional<TokenInfo> loadTokenInfo = jpaTokenRepo.findById(email);
        if(loadTokenInfo.isPresent()){
            TokenInfo tokenInfo = loadTokenInfo.get();
            String token = tokenInfo.getToken();
            Date exp = new Date(tokenInfo.getExpirationTimestamp().getTime());
            try {
                decode.parseJwt(token).getBody().getExpiration();
            }catch (ExpiredJwtException ex) {
                logger.error(ex.getMessage());
            }
            logger.info(token);
            Instant now = Instant.now();
            Date threshold = Date.from(now.plus(2l, ChronoUnit.MINUTES));
            if(exp.after(threshold)) {
                logger.info("Valid token. Use it in Concur.");
            }else if(exp.before(threshold)&&exp.after(Date.from(now))) {
                logger.warn("The token will expire in two minutes! Please refresh token.");
            }
        }else {
            String token = encode.token(email);
            Date expiration = decode.parseJwt(token).getBody().getExpiration();
            setTokenEntities(email, token, expiration);
        }
    }

    //Delete and generate new token
    public void refreshToken(String token){
        Optional<RefreshToken> loadRefresh = jpaRefreshTokenRepo.findById(token);
        if(loadRefresh.isPresent()){
            RefreshToken refreshToken = loadRefresh.get();
            String email = refreshToken.getTokenInfo().getEmail();
            String newToken = encode.token(email);
            Date newExp =  decode.parseJwt(newToken).getBody().getExpiration();
            jpaRefreshTokenRepo.delete(refreshToken);
            updateTokenInfo(email,newToken,newExp);
        }
        //String email = decode.parseJwt(token).getBody().get("email", String.class);
    }

    public void signupUser(TokenInfo tokenInfo, RefreshToken refreshToken){
        jpaTokenRepo.save(tokenInfo);
        jpaRefreshTokenRepo.save(refreshToken);
        logger.info(tokenInfo.getToken());
    }

    public void updateTokenInfo(String email, String token, Date exp){
        setTokenEntities(email, token, exp);
    }

    private void setTokenEntities(String email, String token, Date exp){
        TokenInfo tokenInfo = new TokenInfo();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        tokenInfo.setEmail(email);
        tokenInfo.setToken(token);
        Timestamp ts = new Timestamp(exp.getTime());
        tokenInfo.setExpirationTimestamp(ts);
        refreshToken.setExpirationTimestamp(ts);
        refreshToken.setTokenInfo(tokenInfo);
        signupUser(tokenInfo, refreshToken);
    }
}
